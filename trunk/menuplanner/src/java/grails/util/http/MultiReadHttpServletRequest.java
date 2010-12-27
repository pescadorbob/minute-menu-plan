package grails.util.http;

import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletInputStream;
import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {

    private byte[] body;

    public MultiReadHttpServletRequest(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
        // Read the request body and save it as a byte array
        try {
            InputStream is = super.getInputStream();
            body = IOUtils.toByteArray(is);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStreamImpl(new ByteArrayInputStream(body));
    }

    @Override
    public BufferedReader getReader() throws IOException {
        String enc = getCharacterEncoding();
        if(enc == null) enc = "UTF-8";
        return new BufferedReader(new InputStreamReader(getInputStream(), enc));
    }

    private class ServletInputStreamImpl extends ServletInputStream {

        private InputStream is;

        public ServletInputStreamImpl(InputStream is) {
            this.is = is;
        }

        public int read() throws IOException {
            return is.read();
        }

        public boolean markSupported() {
            return false;
        }

        public synchronized void mark(int i) {
            throw new RuntimeException(new IOException("mark/reset not supported"));
        }

        public synchronized void reset() throws IOException {
            throw new IOException("mark/reset not supported");
        }
    }

}
