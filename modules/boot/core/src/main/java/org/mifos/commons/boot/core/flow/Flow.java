///
/// This Source Code Form is subject to the terms of the Mozilla Public
/// License, v. 2.0. If a copy of the MPL was not distributed with this
/// file, You can obtain one at http://mozilla.org/MPL/2.0/.
///
package org.mifos.commons.boot.core.flow;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import org.mifos.commons.boot.core.model.MifosContext;

@FunctionalInterface
public interface Flow<C extends MifosContext> {
    C run(C context);

    @SafeVarargs
    static <C extends MifosContext> Flow<C> sequence(Flow<C>... steps) {
        return ctx -> {
            for (Flow<C> step : steps) ctx = step.run(ctx);
            return ctx;
        };
    }

    // --- decision ---
    static <C extends MifosContext> Flow<C> when(Predicate<C> cond, Flow<C> then, Flow<C> otherwise) {
        return ctx -> cond.test(ctx) ? then.run(ctx) : otherwise.run(ctx);
    }

    static <C extends MifosContext> Flow<C> when(Predicate<C> cond, Flow<C> then) {
        return when(cond, then, noop());
    }

    // --- iteration ---
    static <C extends MifosContext> Flow<C> repeatWhile(Predicate<C> cond, Flow<C> body) {
        return ctx -> {
            while (cond.test(ctx)) ctx = body.run(ctx);
            return ctx;
        };
    }

    static <C extends MifosContext, I> Flow<C> forEach(
            Function<C, ? extends Collection<I>> items, BiConsumer<I, C> body) {
        return ctx -> {
            for (I item : items.apply(ctx)) body.accept(item, ctx);
            return ctx;
        };
    }

    // --- parallel fork/join ---
    static <C extends MifosContext> Flow<C> forkJoin(
            Function<C, C> snapshot, List<Flow<C>> forks, BinaryOperator<C> merge, Executor executor) {
        return ctx -> {
            List<CompletableFuture<C>> results = forks.stream()
                    .map(fork -> CompletableFuture.supplyAsync(() -> fork.run(snapshot.apply(ctx)), executor))
                    .toList();
            C merged = ctx;
            for (CompletableFuture<C> r : results) merged = merge.apply(merged, r.join());
            return merged;
        };
    }

    // --- helpers / decorator example ---
    static <C extends MifosContext> Flow<C> noop() {
        return ctx -> ctx;
    }

    static <C extends MifosContext> Flow<C> retry(int attempts, Flow<C> flow) {
        return ctx -> {
            RuntimeException last = null;
            for (int i = 1; i <= attempts; i++) {
                try {
                    return flow.run(ctx);
                } catch (RuntimeException e) {
                    last = e;
                }
            }
            throw last;
        };
    }
}
