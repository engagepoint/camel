package org.apache.camel.component.http;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServletOutputStreamTestImpl extends ServletOutputStream {

    private List<Byte> list;

    public ServletOutputStreamTestImpl() {
        super();
        list = new ArrayList<Byte>();
    }

    @Override
    public void write(int b) throws IOException {
        list.add(new Byte((byte)b));
    }

    public List<Byte> getList() {
        return list;
    }

    public void setList(List<Byte> list) {
        this.list = list;
    }

    public String toString() {
        if (list.isEmpty()) {
            return "";
        }
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i);
        }
        return new String(bytes);
    }

}
