package cn.bobdeng.args;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArgsTest {
    //todo 布尔跟参数报错
    //todo 整数跟多余1个参数报错
    //todo 字符串跟多余1个参数报错
    //todo 字符串数组
    //todo 整数数组
    //todo 负数的情形
    @Test
    public void should_return_false_if_flag_not_present() {
        SingleBool result = Args.parse(SingleBool.class);
        assertEquals(result.flag(), false);
    }

    @Test
    public void should_return_true_if_flag_present() {
        SingleBool result = Args.parse(SingleBool.class, "-f");
        assertEquals(result.flag(), true);
    }

    public static record SingleBool(@Option("f") Boolean flag) {
    }

    @Test
    public void should_return_string() {
        SingleString singleString = Args.parse(SingleString.class, "-d", "/usr/local");
        assertEquals(singleString.directory(), "/usr/local");
    }

    public static record SingleString(@Option(value = "d", defaultValue = "/usr/local") String directory) {
    }

    @Test
    public void should_return_default_value_when_not_present() {
        SingleString singleString = Args.parse(SingleString.class);
        assertEquals(singleString.directory(), "/usr/local");
    }

    @Test
    public void should_return_int() {
        SingleInteger singleInteger = Args.parse(SingleInteger.class, "-p", "8080");
        assertEquals(singleInteger.port(), 8080);
    }

    public static record SingleInteger(@Option(value = "p", defaultValue = "8080") Integer port) {
    }

    @Test
    public void should_return_default_int_value_when_not_present() {
        SingleInteger singleInteger = Args.parse(SingleInteger.class);
        assertEquals(singleInteger.port(), 8080);
    }

    //多个组合
    @Test
    public void should_parse_multi() {
        MultiValues multiValues = Args.parse(MultiValues.class, "-f", "-d", "/user/local", "-p", "8080");
        assertEquals(multiValues.flag(), true);
        assertEquals(multiValues.directory(), "/user/local");
        assertEquals(multiValues.port(), 8080);
    }

    public static record MultiValues(@Option("f") Boolean flag, @Option("d") String directory,
                                     @Option("p") Integer port) {
    }

}
