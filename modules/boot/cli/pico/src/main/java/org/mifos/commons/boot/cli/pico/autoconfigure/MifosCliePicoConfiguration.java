///
/// This Source Code Form is subject to the terms of the Mozilla Public
/// License, v. 2.0. If a copy of the MPL was not distributed with this
/// file, You can obtain one at http://mozilla.org/MPL/2.0/.
///
package org.mifos.commons.boot.cli.pico.autoconfigure;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.jline.builtins.Completers;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.Parser;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.Status;
import org.mifos.commons.boot.cli.pico.command.MifosCliPicoCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import picocli.CommandLine;

@Configuration
class MifosCliePicoConfiguration {
    private final CommandLine.Help.ColorScheme colorScheme = new CommandLine.Help.ColorScheme.Builder()
            .commands(CommandLine.Help.Ansi.Style.bold, CommandLine.Help.Ansi.Style.underline)
            .options(CommandLine.Help.Ansi.Style.fg_yellow)
            .parameters(CommandLine.Help.Ansi.Style.fg_yellow)
            .optionParams(CommandLine.Help.Ansi.Style.italic)
            .errors(CommandLine.Help.Ansi.Style.fg_red, CommandLine.Help.Ansi.Style.bold)
            .stackTraces(CommandLine.Help.Ansi.Style.italic)
            .applySystemProperties()
            .build();

    @Bean
    Completer filesCompleter() {
        return new Completers.FilesCompleter(Path.of("./"));
    }

    @Bean
    Completer directoriesCompleter() {
        return new Completers.DirectoriesCompleter(Path.of("./"));
    }

    @Bean
    Completer fooCompleter() {
        return new StringsCompleter("foo", "bar", "baz");
    }

    @Bean
    Terminal terminal() throws IOException {
        return TerminalBuilder.builder().build();
    }

    @Bean
    Parser parser() {
        return new DefaultParser();
    }

    @Bean
    Status status(Terminal terminal) {
        return Status.getStatus(terminal);
    }

    @Bean
    CommandLine commandLine(MifosCliPicoCommand command, CommandLine.IFactory factory) {
        return new CommandLine(command, factory).setColorScheme(colorScheme);
    }

    @Bean
    LineReader lineReader(Terminal terminal, Parser parser, List<Completer> completers) {
        return LineReaderBuilder.builder()
                .terminal(terminal)
                .parser(parser)
                .completer(new AggregateCompleter(completers))
                .option(LineReader.Option.HISTORY_IGNORE_DUPS, false)
                .variable(LineReader.LIST_MAX, 50)
                .history(new DefaultHistory())
                .variable(LineReader.HISTORY_FILE, Path.of("build/.mfg-history"))
                .variable(LineReader.HISTORY_SIZE, 500)
                .build();
    }
}
