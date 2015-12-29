package com.education.experiment.cloudlibrary;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.MaxWordAnalyzer;
import com.education.experiment.commons.UserBean;

public class RetrievalBooksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Analyzer analyzer = new MaxWordAnalyzer();
	private static final File index = new File("/hadoop/indexes/index");

	public RetrievalBooksServlet() {
	}

	/*
	 * 处理客户端查询课本的请求，服务端接收到该请求后，然后读取本地的索引文件，把查询到的结果返回给客户端.
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request.setCharacterEncoding(Charset.defaultCharset().toString());
		request.setCharacterEncoding("utf-8");
		UserBean ub = (UserBean) request.getSession().getAttribute("user");
		int pageNumber = 1;
		String pageNumberStr = request.getParameter("pageNumber");
		if (pageNumberStr != null && !pageNumberStr.equals("")) {
			pageNumber = Integer.parseInt(pageNumberStr);
		}
		if (ub == null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		} else {
			if (!index.exists()) {
				request.setAttribute("result", null);
				request.getRequestDispatcher("/error.jsp?result=索引文件不存在!").forward(request, response);
			} else {
				// 获取客户端的查询对象
				Book book = new Book();
				book.setName(new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8"));
				book.setAuthor(new String(request.getParameter("author").getBytes("iso-8859-1"), "UTF-8"));
				book.setPublishDate(new String(request.getParameter("publishdate").getBytes("iso-8859-1"), "UTF-8"));
				book.setSection(new String(request.getParameter("section").getBytes("iso-8859-1"), "UTF-8"));
				request.setAttribute("book", book);
				List<Book> list = null;
				try {
					// 根据查询对象在索引文件里进行相关查询，把结果封装到一个list当中，返回给客户端
					list = retrievalBooks(request, book, pageNumber);
					request.setAttribute("entryList", list);
					if (list.size() > 0) {
						request.setAttribute("result", "");
					} else {
						request.setAttribute("result", null);
					}
					request.getRequestDispatcher("/retrievalbooks.jsp").forward(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					request.getRequestDispatcher("/error.jsp?result=检索索引出现异常信息.").forward(request, response);
				}
			}
		}
	}

	// 查询索引文件的方法，从索引文件里读取index文件，然后根据客户端提交的条件进行查询，把符合结果的数据返回
	private List<Book> retrievalBooks(HttpServletRequest request, Book quote, int pageNumber) throws Exception {
		List<String> list = new ArrayList<String>();
		String keywords = "";
		if (quote.getName() != null && !"".equals(quote.getName())) {
			list.add("name");
			keywords += quote.getName() + ",";
		}
		if (quote.getAuthor() != null && !"".equals(quote.getAuthor())) {
			list.add("author");
			keywords += quote.getAuthor() + ",";
		}
		if (quote.getPublishDate() != null && !"".equals(quote.getPublishDate())) {
			list.add("publishdate");
			keywords += quote.getPublishDate() + ",";
		}
		if (quote.getSection() != null && !"".equals(quote.getSection())) {
			list.add("sections");
			keywords += quote.getSection();
		}
		String[] fieldArray = (String[]) list.toArray(new String[list.size()]);
		String[] keywordArray = keywords.split(",");
		BooleanQuery bQuery = new BooleanQuery(); // 组合查询
		BooleanClause.Occur[] flags = new BooleanClause.Occur[list.size()];
		for (int index = 0; index < flags.length; index++) {
			flags[index] = BooleanClause.Occur.MUST;
		}
		Query query = MultiFieldQueryParser.parse(Version.LUCENE_42, keywordArray, fieldArray, flags, analyzer);
		bQuery.add(query, BooleanClause.Occur.MUST);

		// 获取访问索引的接口,进行搜索
		Directory directory = FSDirectory.open(index);
	    IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		int pageSize = 10;
		int start = (pageNumber - 1) * pageSize;
		int hm = start + pageSize;
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("pageNumber", pageNumber);

		// TopDocs 搜索返回的结果
		TopScoreDocCollector res = TopScoreDocCollector.create(hm, false);

		indexSearcher.search(bQuery, res);// 只返回前100条记录
		TopDocs tds = res.topDocs(start, pageSize);

		int totalCount = res.getTotalHits();
		request.setAttribute("totalPosts", totalCount);
		int pages = (totalCount - 1) / pageSize + 1; // 计算总页数
		request.setAttribute("totalPages", pages);
		System.out.println("搜索到的结果总数量为：" + totalCount);
		System.out.println("总页数为：" + pages);

		ScoreDoc[] scoreDocs = tds.scoreDocs; // 搜索的结果列表
		System.out.println(scoreDocs.length);
		// 创建高亮器,使搜索的关键词突出显示
		Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
		Scorer fragmentScore = new QueryScorer(bQuery);
		Highlighter highlighter = new Highlighter(formatter, fragmentScore);

		Fragmenter fragmenter = new SimpleFragmenter(100);
		highlighter.setTextFragmenter(fragmenter);

		List<Book> books = new ArrayList<Book>();
		// 把搜索结果取出放入到集合中,并对关键词进行高亮显示
		for (ScoreDoc scoreDoc : scoreDocs) {
			int docID = scoreDoc.doc;// 当前结果的文档编号
			Document document = indexSearcher.doc(docID);
			Book book = new Book();
			String name = document.get("name");
			String highlighterName = highlighter.getBestFragment(analyzer, "name", name);
			if (highlighterName == null) {
				highlighterName = name;
			}
			book.setName(highlighterName);

			String author = document.get("author");
			String highlighterAuthor = highlighter.getBestFragment(analyzer, "author", author);
			if (highlighterAuthor == null) {
				highlighterAuthor = author;
			}
			book.setAuthor(highlighterAuthor);

			String publishdate = document.get("publishdate");
			String highlighterPublishdate = highlighter.getBestFragment(analyzer, "publishdate", publishdate);
			if (highlighterPublishdate == null) {
				highlighterPublishdate = publishdate;
			}
			book.setPublishDate(highlighterPublishdate);

			String section = document.get("sections");
			String highlighterSections = highlighter.getBestFragment(analyzer, "sections", section);

			if (highlighterSections == null) {
				if (section.length() > 150) {
					highlighterSections = section.substring(0, 150);
				} else {
					highlighterSections = section;
				}
			}
			book.setSection(highlighterSections);

			books.add(book);
		}
		indexReader.close();
		return books;
	}
}
