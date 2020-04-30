<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="ws.connecter" import="java.net.URI" import="org.json.*"%>

<%String sendMsg="{\"signal\": "+request.getParameter("signal")+", \"message\": "+request.getParameter("message")+"}"; %>
<jsp:useBean id="connInfo" class="ws.connInfoBean" scope="application"/>

<jsp:useBean id="client" class="ws.clientBean" scope="page" >
  <jsp:setProperty name="client" property="ipString" value="<%=connInfo.getIp() %>" />
  <jsp:setProperty name="client" property="portString" value="<%=connInfo.getPort() %>" />
  <jsp:setProperty name="client" property="sendMsgString" value="<%=sendMsg %>" />
  <jsp:setProperty name="client" property="rsaPrivateString" value="<%=connInfo.getRsaPrivateKey() %>" />
</jsp:useBean>

<%
	connecter c=client.getC();
	out.print(c.getMsg());
	JSONObject json = new JSONObject(c.getMsg());
	String s = json.getString("message");
	JSONArray l = json.getJSONArray("content");
	if(s.equals("LoginSuccess")){
		session.setAttribute("id", l.getString(0));
	}
%>
