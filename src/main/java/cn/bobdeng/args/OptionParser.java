package cn.bobdeng.args;

import java.util.List;

public interface OptionParser {
    Object parse(Option option, List<String> args);
}
