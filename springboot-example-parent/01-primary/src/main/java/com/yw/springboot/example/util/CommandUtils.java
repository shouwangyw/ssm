package com.yw.springboot.example.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author yangwei
 */
@Slf4j
public class CommandUtils {
    private CommandUtils() {}

    public static String exec(String command) throws Exception {
        log.info("command: {}", command);
        Process process = Runtime.getRuntime().exec(command);

        List<String> lines = IOUtils.readLines(process.getInputStream(), "GBK");
        process.waitFor();

        return StringUtils.join(lines, System.lineSeparator());

    }
}