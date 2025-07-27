package org.gbl.catalog.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import org.springframework.util.StringUtils;

import java.util.List;

public class FlightElkQueryBuilder {

    public static Query build(List<Query> queries) {
        return QueryBuilders.bool(b -> b.must(queries));
    }

    public static Query availableOnly() {
        return QueryBuilders.term(t -> t.field("available").value(true));
    }

    public static Query destinationOn(String destination) {
        return QueryBuilders.term(t -> t.field("destination").value(destination));
    }

    public static Query originatedOn(String origin) {
        if (!StringUtils.hasText(origin)) {
            return Query.of(it -> null);
        }
        return QueryBuilders.term(t -> t.field("origin").value(origin));
    }
}
