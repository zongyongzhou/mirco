package org.integrain.sm.gateway.fallback;

import java.io.ByteArrayInputStream;

import java.io.IOException;

import java.io.InputStream;



import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;

import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;

import org.springframework.http.client.ClientHttpResponse;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;




@Component
public class ServerFallback implements ZuulFallbackProvider {

	@Override
	public String getRoute() {
		// TODO Auto-generated method stub
		return "*";
	}

	@Override

	public ClientHttpResponse fallbackResponse() {

		

		return new ClientHttpResponse(){



			@Override

			public InputStream getBody() throws IOException {

				JSONObject r = new JSONObject();

				r.put("state", "9999");

				r.put("msg", "系统错误，请求失败");

				return new ByteArrayInputStream(r.toJSONString().getBytes("UTF-8"));

			}



			@Override

			public HttpHeaders getHeaders() {

				HttpHeaders headers = new HttpHeaders();

				//和body中的内容编码一致，否则容易乱码

		        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

		        return headers;

			}



			/**

			 * 网关向api服务请求是失败了，但是客户端向网关发起的请求是OK的，

			 * 不应该把api的404,500等问题抛给客户端

			 * 网关和api服务集群对于客户端来说是黑盒子

			 */

			@Override

			public HttpStatus getStatusCode() throws IOException {

				

				return HttpStatus.OK;

			}



			@Override

			public int getRawStatusCode() throws IOException {

				

				return HttpStatus.OK.value();

			}



			@Override

			public String getStatusText() throws IOException {

				

				return HttpStatus.OK.getReasonPhrase();

			}



			@Override

			public void close() {

				

				

			}

			

		};

	}


	
}
