package de.hs_mannheim.informatik.lambda.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.hadoop.fs.Path;

@Controller("/hdfs")
public class HdfsTestController {
    



    @GetMapping("/test")
    public String test() throws IOException {
        var configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://localhost:9000");
        var fileSystem = FileSystem.get(configuration);
        var directoryName = "javadeveloperzone/javareadwriteexample";
        var path = new Path(directoryName);
        fileSystem.mkdirs(path);




        String fileName = "read_write_hdfs_example.txt";
        Path hdfsWritePath = new Path("/user/javadeveloperzone/javareadwriteexample/" + fileName);
        FSDataOutputStream fsDataOutputStream = fileSystem.create(hdfsWritePath,true);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fsDataOutputStream,StandardCharsets.UTF_8));
        bufferedWriter.write("Java API to write data in HDFS");
        bufferedWriter.newLine();
        bufferedWriter.close();
        fileSystem.close();








        return "Hello World";
    }

    
}
