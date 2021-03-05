package com.fortitudetec.junit.pioneering;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.Streams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIn;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

@DisplayName("@StdIo")
class StandardIOTest {

    @Test
    @StdIo({"foo", "bar", "baz"})
    void shouldReadFromStandardInput() throws IOException {
        var reader = new BufferedReader(
                new InputStreamReader(System.in));
        assertThat(reader.readLine()).isEqualTo("foo");
        assertThat(reader.readLine()).isEqualTo("bar");
        assertThat(reader.readLine()).isEqualTo("baz");
    }

    @Test
    @StdIo({"1", "2", "3"})
    void shouldCaptureStdIn(StdIn stdIn) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));
        reader.readLine();
        reader.readLine();
        reader.readLine();

        assertThat(stdIn.capturedLines()).containsExactly("1", "2", "3");
    }

    @Test
    @StdIo
    void shouldInterceptStandardOutput(StdOut stdOut) {
        System.out.println("The answer is 24");
        System.out.println("No, the real answer is always 42");

        assertThat(stdOut.capturedLines()).containsExactly(
                "The answer is 24",
                "No, the real answer is always 42"
        );
    }

    @Test
    @StdIo({"What's the weather like?", "No thanks.", "Thanks."})
    void shouldReadStandardInputAndInterceptStandardOutput(StdIn stdIn, StdOut stdOut) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));

        var question = reader.readLine();
        System.out.println("Currently it's 48 degrees and cloudy. Would you like to hear tomorrow's forecast?");
        var wantTomorrowForecast = reader.readLine();
        System.out.println("Ok, enjoy the rest of your day!");
        var extraGoodbye = reader.readLine();

        assertThat(question).isEqualTo("What's the weather like?");
        assertThat(wantTomorrowForecast).isEqualTo("No thanks.");
        assertThat(extraGoodbye).isEqualTo("Thanks.");


        //noinspection UnstableApiUsage
        var transcript = Streams.zip(
                Arrays.stream(stdIn.capturedLines()),
                Arrays.stream(stdOut.capturedLines()),
                (q, a) -> q + " " + a
        ).collect(toList());

        assertThat(transcript).containsExactly(
                "What's the weather like? Currently it's 48 degrees and cloudy. Would you like to hear tomorrow's forecast?",
                "No thanks. Ok, enjoy the rest of your day!"
        );

        assertThat(stdIn.capturedLines()[2]).contains("Thanks.");
    }
}
