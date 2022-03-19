package cn.bobdeng.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Args {
    public static <T> T parse(Class<T> target, String... args) {
        try {
            Constructor<?> constructor = target.getConstructors()[0];
            Object[] values = Stream.of(constructor.getParameters())
                    .map(parameter -> getParameterValue(parameter, args))
                    .toArray();
            return (T) constructor.newInstance(values);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getParameterValue(Parameter parameter, String[] args) {
        Option option = parameter.getAnnotation(Option.class);
        List<String> argsList = Arrays.asList(args);
        return optionsParsers.get(parameter.getType()).parse(option, argsList);
    }

    static Map<Class, OptionParser> optionsParsers = Map.of(
            Boolean.class, Args::getBoolean,
            Integer.class, Args::getInteger,
            String.class, Args::getStringValue
    );

    private static String getStringValue(Option option, List<String> argsList) {
        int index = argsList.indexOf("-" + option.value());
        if (index == -1) {
            return option.defaultValue();
        }
        return argsList.get(index + 1);
    }

    private static int getInteger(Option option, List<String> argsList) {
        int index = argsList.indexOf("-" + option.value());
        if (index == -1) {
            return Integer.parseInt(option.defaultValue());
        }
        return Integer.parseInt(argsList.get(index + 1));
    }

    private static boolean getBoolean(Option option, List<String> argsList) {
        int index = argsList.indexOf("-" + option.value());
        return index >= 0;
    }
}
