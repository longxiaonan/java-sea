package com.iee.es.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.iee.es.dao.BookDao;
import com.iee.es.dao.BookDao2;
import com.iee.es.entity.Book;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FieldValueFactorFunction;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BookDao2 bookDao2;

    /**
     * 1、查  id
     * GET: http://127.0.0.1:8080/book/get/7
     *
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Book getBookById(@PathVariable String id) {
        Optional<Book> byId = bookDao.findById(id);
        return byId.orElse(new Book());
    }

    /**
     * 2、查  ++:全文检索（根据整个实体的所有属性，可能结果为0个）
     *
     * @param q 搜索关键字
     * @return
     */
    @GetMapping("/select/{q}")
    public List<Book> testSearch(@PathVariable String q) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(q);
        Iterable<Book> searchResult = bookDao.search(builder);
        Iterator<Book> iterator = searchResult.iterator();
        List<Book> list = new ArrayList<Book>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    /**
     * 3、查   +++：分页、分数、分域（结果一个也不少）
     *
     * @param page
     * @param size
     * @param q
     * @return
     */
    @GetMapping("/{page}/{size}/{q}")
    public List<Book> searchBook(@PathVariable Integer page, @PathVariable Integer size, @PathVariable String q) {

//        Sort.Order order = new SortBuilders.Order(Sort.Direction.ASC, "id");
//        Sort sort = Sort.by(order);
//        FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort("id").

        // 分页参数
//        Pageable pageable = PageRequest.of(page, size, sort);
        Pageable pageable = PageRequest.of(page, size);

        // 分数，并自动按分排序
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.matchQuery("type", "计算机"));

        ScoreFunctionBuilder<?> scoreFunctionBuilder = ScoreFunctionBuilders
                .fieldValueFactorFunction("name").modifier(FieldValueFactorFunction.Modifier.LN1P).factor(0.1f);
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders
//                .functionScoreQuery(queryBuilder, scoreFunctionBuilder).boostMode(CombineFunction.SUM);
                .functionScoreQuery(queryBuilder, scoreFunctionBuilder);

        // 分数、分页
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(functionScoreQueryBuilder).build();
        //字符串的查询
//        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders
//                .queryStringQuery("spring boot OR 书籍")).withPageable(pageable).withSort(fieldSortBuilder).build();
        Page<Book> searchPageResults = bookDao.search(searchQuery);
        return searchPageResults.getContent();

    }

    /**
     * 4、增
     *
     * @param book
     * @return
     */
    @PostMapping("/insert")
    public Book insertBook(@RequestBody Book book) {
        bookDao.save(book);
        return book;
    }

    /**
     * 5、删 id
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Book insertBook(@PathVariable String id) {
        Book book = bookDao.findById(id).get();
        bookDao.delete(book);
        return book;
    }

    /**
     * 删除所有book类型的document
     **/
    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        bookDao.deleteAll();
    }

    /**
     * 6、改
     *
     * @param book
     * @return
     */
    @PutMapping("/update")
    public Book updateBook(Book book) {
        bookDao.save(book);
        return book;
    }

}