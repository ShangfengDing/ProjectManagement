<%@ page language="java" contentType="text/html; charset=UTF-8"
		 import="com.baidu.ueditor.ActionEnter,java.util.List,java.util.ArrayList,org.apache.commons.io.FileUtils"
		 pageEncoding="UTF-8"%>
<%@ page import="java.io.*"%>
<%@ page import="org.apache.commons.fileupload.*"%>

<%@ page import="org.apache.commons.fileupload.servlet.*"%>
<%@ page import="org.apache.commons.fileupload.FileItemIterator"%>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@ page import="java.io.BufferedInputStream"%>
<%@ page import="java.io.BufferedOutputStream"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.InputStream"%>

<%@ page import="java.io.FileOutputStream"%>

<%@ page import="com.free4lab.utils.http.DiskClient"%>
<%@ page import="com.baidu.ueditor.define.BaseState"%>
<%@ page import="com.baidu.ueditor.define.AppInfo" %>
<%@ page import="com.baidu.ueditor.define.State" %>
<%@ page import="com.baidu.ueditor.define.FileType" %>
<%@ page import="java.util.Collections" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%

//	int a = Pattern.CANON_EQ;
	request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");

	String rootPath = application.getRealPath( "/" );

	if("uploadimage".equalsIgnoreCase(request.getParameter("action"))){
		FileItemStream fileStream = null;
		boolean isAjaxUpload = request.getHeader( "X_Requested_With" ) != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			out.write(new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT).toJSONString());
		}else{
			ServletFileUpload upload = new ServletFileUpload(
					new DiskFileItemFactory());

			if ( isAjaxUpload ) {
				upload.setHeaderEncoding( "UTF-8" );
			}

			try {
				FileItemIterator iterator = upload.getItemIterator(request);

				while (iterator.hasNext()) {
					fileStream = iterator.next();

					if (!fileStream.isFormField())
						break;
					fileStream = null;
				}

				if (fileStream == null) {
					out.println(new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA));
				}

				String originFileName = fileStream.getName();
				String suffix = FileType.getSuffixByFilename(originFileName);
//
				originFileName = originFileName.substring(0,
						originFileName.length() - suffix.length());

				long maxSize = 204800000;

				String[] allowTypes = new String[]{".png", ".jpg", ".jpeg", ".gif", ".bmp"};
				List<String> list = new ArrayList<String>();
				Collections.addAll(list, allowTypes);
				if (!list.contains(suffix)) {
					out.write(new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE).toJSONString());
				}

				InputStream is = fileStream.openStream();
				State state = null;
				File tmpDir = FileUtils.getTempDirectory();
				String tmpFileName = (Math.random() * 10000 + "").replace(".", "");
				File tmpFile =  new File(tmpDir, tmpFileName);

				byte[] dataBuf = new byte[ 2048 ];
				BufferedInputStream bis = new BufferedInputStream(is, 8192);

				try {
					BufferedOutputStream bos = new BufferedOutputStream(
							new FileOutputStream(tmpFile), 8192);

					int count = 0;
					while ((count = bis.read(dataBuf)) != -1) {
						bos.write(dataBuf, 0, count);
					}
					bos.flush();
					bos.close();

					if (tmpFile.length() > maxSize) {
						tmpFile.delete();
						out.write(new BaseState(false, AppInfo.MAX_SIZE).toJSONString());
					}

					String uuid = "";
					try {
						uuid = DiskClient.upload(tmpFile, originFileName, null);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					state = new BaseState(true);
					state.putInfo( "size", tmpFile.length() );
					state.putInfo( "title", originFileName );
					state.putInfo("uuid", uuid);
					if (state.isSuccess()) {
						tmpFile.delete();
					}

					is.close();
					if (state.isSuccess()) {
						state.putInfo("url", "http://freedisk.free4inno.com/download?uuid=" + uuid);
						state.putInfo("type", suffix);
						state.putInfo("original", originFileName + suffix);
					}

					out.write(state.toJSONString());
					return ;
				} catch (IOException e) {
					out.write(new BaseState(false, AppInfo.IO_ERROR).toJSONString());
				}

			} catch (FileUploadException e) {
				out.write(new BaseState(false, AppInfo.PARSE_REQUEST_ERROR).toJSONString());
			} catch (Exception e) {

			}

			out.write(new BaseState(false, AppInfo.IO_ERROR).toJSONString());
		}



	}else{
		out.write( new ActionEnter( request, rootPath ).exec() );
	}
%>