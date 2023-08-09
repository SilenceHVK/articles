package me.hvkcoder.java_basic.io.netty.tomcat.servlet;

import me.hvkcoder.java_basic.io.netty.tomcat.http.TomcatRequest;
import me.hvkcoder.java_basic.io.netty.tomcat.http.TomcatResponse;
import me.hvkcoder.java_basic.io.netty.tomcat.http.TomcatServlet;

/**
 * @author h_vk
 * @since 2023/8/9
 */
public class FirstServlet extends TomcatServlet {
	@Override
	public void doGet(TomcatRequest request, TomcatResponse response) throws Exception {
		response.write("FirstServlet GET");
	}

	@Override
	public void doPost(TomcatRequest request, TomcatResponse response) throws Exception {
		response.write("FirstServlet POST");
	}
}
