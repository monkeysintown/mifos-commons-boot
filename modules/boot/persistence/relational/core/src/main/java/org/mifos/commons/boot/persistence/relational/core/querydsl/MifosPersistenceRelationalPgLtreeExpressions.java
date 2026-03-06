/**
 * This Source Code Form is subject to the terms of the Mozilla Public License, v. 2.0. If a copy of the MPL was not
 * distributed with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifos.commons.boot.persistence.relational.core.querydsl;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;

/**
 * PostgreSQL ltree operators for QueryDSL.
 *
 * <p>Operators: - {@literal @>} : ltree contains (ancestor) - {@literal <@} : ltree is contained by (descendant) - || :
 * ltree concatenation - nlevel() : number of levels in path - subpath() : extract portion of path.
 */
public final class MifosPersistenceRelationalPgLtreeExpressions {

    private MifosPersistenceRelationalPgLtreeExpressions() {}

    public static BooleanExpression contains(StringPath path, String ancestorPath) {
        return Expressions.booleanTemplate(
                "cast({0} as ltree) @> cast({1} as ltree)", path, Expressions.constant(ancestorPath));
    }

    public static BooleanExpression contains(StringPath path, Expression<String> ancestorPath) {
        return Expressions.booleanTemplate("cast({0} as ltree) @> cast({1} as ltree)", path, ancestorPath);
    }

    public static BooleanExpression containedBy(StringPath path, String descendantPath) {
        return Expressions.booleanTemplate(
                "cast({0} as ltree) <@ cast({1} as ltree)", path, Expressions.constant(descendantPath));
    }

    public static BooleanExpression containedBy(StringPath path, Expression<String> descendantPath) {
        return Expressions.booleanTemplate("cast({0} as ltree) <@ cast({1} as ltree)", path, descendantPath);
    }

    public static BooleanExpression matches(StringPath path, String lqueryPattern) {
        return Expressions.booleanTemplate("cast({0} as ltree) ~ {1}", path, Expressions.constant(lqueryPattern));
    }

    public static Expression<Integer> nlevel(StringPath path) {
        return Expressions.numberTemplate(Integer.class, "nlevel(cast({0} as ltree))", path);
    }

    public static Expression<String> subpath(StringPath path, int start) {
        return Expressions.stringTemplate("subpath(cast({0} as ltree), {1})", path, Expressions.constant(start));
    }

    public static Expression<String> subpath(StringPath path, int start, int end) {
        return Expressions.stringTemplate(
                "subpath(cast({0} as ltree), {1}, {2})", path, Expressions.constant(start), Expressions.constant(end));
    }

    public static Expression<String> concat(StringPath path1, String path2) {
        return Expressions.stringTemplate(
                "cast({0} as ltree) || cast({1} as ltree)", path1, Expressions.constant(path2));
    }

    public static Expression<String> text2ltree(Expression<String> text) {
        return Expressions.stringTemplate("text2ltree({0})", text);
    }

    public static Expression<String> ltree2text(StringPath path) {
        return Expressions.stringTemplate("ltree2text(cast({0} as ltree))", path);
    }
}
