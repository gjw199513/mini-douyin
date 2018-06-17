package com.gjw.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gjw19 on 2018/6/17.
 */
public class FFMpegTest {

    private String ffmpegEXE;

    public FFMpegTest(String ffmpegEXE) {
        this.ffmpegEXE = ffmpegEXE;
    }

    public void convertor(String videoInputPath, String videoOutputPath) throws IOException {
        // ffmpeg -i input.mp4 output.avi
        // 添加执行的命令
        List<String> command = new ArrayList<>();
        command.add(ffmpegEXE);

        command.add("-i");
        command.add(videoInputPath);
        command.add(videoOutputPath);

        for (String c : command){
            System.out.println(c);
        }

        // 执行命令
        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();

        // 处理执行命令的错误流
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line = "";
        while((line = br.readLine()) != null){
        }

        if(br != null){
            br.close();
        }
        if(errorStream != null){
            errorStream.close();
        }
        if(inputStreamReader != null){
            inputStreamReader.close();
        }
    }

    public static void main(String[] args) {
        FFMpegTest ffmpeg = new FFMpegTest("D:\\ffmpeg\\bin\\ffmpeg.exe");
        try {
            ffmpeg.convertor("D:\\ffmpeg\\bin\\aa.mp4","D:\\ffmpeg\\bin\\aa.avi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
