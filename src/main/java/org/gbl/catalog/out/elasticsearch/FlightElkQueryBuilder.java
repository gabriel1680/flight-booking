package org.gbl.catalog.out.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import org.springframework.util.StringUtils;

import java.util.List;

public class FlightElkQueryBuilder {

    private static final Query NOOP = QueryBuilders.matchAll().build()._toQuery();

    public static Query build(List<Query> queries) {
        return QueryBuilders.bool(b -> b.must(queries));
    }

    public static Query availableOnly() {
        return QueryBuilders.term(t -> t.field("isAvailable").value(true));
    }

    public static Query destinationTo(String destination) {
        if (!StringUtils.hasText(destination)) {
            return NOOP;
        }
        return QueryBuilders.queryString(t -> t.fields("destination").query("*" + destination + "*"));
    }

    public static Query originatedOn(String origin) {
        if (!StringUtils.hasText(origin)) {
            return NOOP;
        }
        return QueryBuilders.queryString(t -> t.fields("origin").query("*" + origin + "*"));
    }
}
