<%
  String path = request.getScheme() + "://" + request.getServerName()
          + ":" + request.getServerPort() + request.getContextPath()
          + "/";
  response.sendRedirect(path+"login");
   //request.getRequestDispatcher("/login").forward(request,response);
%>