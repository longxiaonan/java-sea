@Field注解的定义如下：

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface Field {

FieldType type() default FieldType.Auto;#自动检测属性的类型

FieldIndex index() default FieldIndex.analyzed;#默认情况下分词

DateFormat format() default DateFormat.none;

String pattern() default "";

boolean store() default false;#默认情况下不存储原文

String searchAnalyzer() default "";#指定字段搜索时使用的分词器

String indexAnalyzer() default "";#指定字段建立索引时指定的分词器

String[] ignoreFields() default {};#如果某个字段需要被忽略

boolean includeInParent() default false;
}