import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;


public class ReadTest {

    public static Configuration conf = new Configuration();
    public static FileSystem fs;

    static {
        try {
            fs = FileSystem.get(new URI("hdfs://192.168.134.128"),conf,"hadoop");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    //seek，按字节跳到指定位置，回车也算一个位置，0的话就从第一个开始读，3的话就4开始读
    //流文件读一次之后再继续读的话什么都读不到
    public void seek() throws Exception {
        FSDataInputStream in = fs.open(new Path("/app/readme.md"));
        IOUtils.copyBytes(in,System.out,1024,false);
        in.seek(0);
//        IOUtils.copyBytes(in,System.out,1024,false);I
        IOUtils.closeStream(in);
    }

    @Test
    //指定副本数（最小blocksize）最小不能小于1MB=1024KB=1048576B
    public void writeData() throws IOException {
        Configuration conf = new Configuration();
        //解决用户权限问题
//        System.setProperty("HADOOP_USER_NAME","hadoop");
//        FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path("/app/test.md"), true, 1024, (short) 2, 5);    报错！
        FSDataOutputStream fsDataOutputStream = fs.create(new Path("/app/test.md"), true, 1024, (short) 2, 1048576);
        fsDataOutputStream.write("hello hadoop".getBytes());
        fsDataOutputStream.close();
    }

    @Test
    public void copy() throws IOException {
        String source = "D:\\workpalce\\java\\README.md";
        String path = "/app/abc.txt";
        FSDataOutputStream fsDataOutputStream = fs.create(new Path(path));
        InputStream in = new FileInputStream(new File(source));
        byte[] b = new byte[1024];
        int len;
        while((len=in.read(b)) != -1) {
            fsDataOutputStream.write(b,0,len);
        }
        in.close();
        fsDataOutputStream.close();
    }

    @Test
    public void mkdirs() throws IOException {
        boolean mkdirs = fs.mkdirs(new Path("/app/mkdir/123/"));
        System.out.println(mkdirs);
    }

}
