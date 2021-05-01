# Sreaching on database with Specification

### This project contains: 

- [Mapstruct](https://mapstruct.org/)
- [Specification](https://spring.io/blog/2011/04/26/advanced-spring-data-jpa-specifications-and-querydsl/)
- [swagger](https://swagger.io/)
- [Spring MVC](https://spring.io/guides/gs/serving-web-content/)
- [PostgreSQL](https://www.postgresql.org/)

## How to use

- you create your restController and extends  ``` SearchController ```</br>
- you create your repository and extends  ``` SearchRepository ```</br>
- you create your service and extends  ``` SearchService ```</br>
- you create your mapper and extends  ``` CommonMapper ```</br>


## Json input structure

```
{
  "condition": "AND",
  "rules": [
    {
      "value": "اکبری",
      "field": "lastName",
      "operator": "LIKE"
    },
    {
      "condition": "AND",
      "rules": [
        {
          "value": "رضا",
          "field": "firstName",
          "operator": "LIKE"
        },
        {
          "value": "مدی",
          "field": "job.name",
          "operator": "LIKE"
        }
      ]
    }
  ]
}
```

## your resultData is like your DTO </br>

You can use [Angular Query-builder](https://www.npmjs.com/package/angular2-query-builder)</br>
[Demo](https://zebzhao.github.io/Angular-QueryBuilder/demo/)
