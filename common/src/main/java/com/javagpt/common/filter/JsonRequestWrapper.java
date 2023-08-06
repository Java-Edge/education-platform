package com.javagpt.common.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.*;

/**
 * @author hmy
 */
public class JsonRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public JsonRequestWrapper(HttpServletRequest request)  {
        super(request);
        body =getBody(request);
    }

    private static byte[] getBody(HttpServletRequest request){
        byte[] temp;
        try {
            temp=StreamUtils.copyToByteArray(request.getInputStream());
        } catch (IOException e) {
            temp=new byte[0];
        }
        return temp;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream byteArrayInputStream = new  ByteArrayInputStream(body);
        return new MyServletInputStream(byteArrayInputStream);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }


    private  static  class MyServletInputStream extends ServletInputStream {

        private final InputStream sourceStream;
        private boolean finished = false;

        public MyServletInputStream(InputStream sourceStream) {
            this.sourceStream = sourceStream;
        }

        public final InputStream getSourceStream() {
            return this.sourceStream;
        }

        @Override
        public int read() throws IOException {
            int data = this.sourceStream.read();
            if (data == -1) {
                this.finished = true;
            }
            return data;
        }

        @Override
        public int available() throws IOException {
            return this.sourceStream.available();
        }

        @Override
        public void close() throws IOException {
            super.close();
            this.sourceStream.close();
        }

        @Override
        public boolean isFinished() {
            return this.finished;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }
    }
}