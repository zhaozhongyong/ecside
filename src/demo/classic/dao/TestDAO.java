package demo.classic.dao;

import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ecside.common.log.LogHandler;
import org.ecside.easyda.DataAccessUtil;
import org.ecside.easydataaccess.ConnectionUtils;
import org.ecside.util.ExportViewUtils;

public class TestDAO extends BaseDAO  {

	private static Log logger = LogFactory.getLog(BaseDAO.class);

	public static int sheetSize=10000;
		

	public List getSomeUserInfo(int num,int start,int end){
		StringBuffer bufSql = new StringBuffer();
		bufSql.append("select * from (select rownum RN,CUSTOMER_ID,FIRST_NAME,CITY_CODE,IDENTITY_CODE,CREATE_DATE ");
		bufSql.append(" from bb_customer_info_t where rownum<=? and rownum<=? ) where RN>=? ");
		
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		
		List resultList=new ArrayList();
		
		try {

			conn = getConnection();
			pstmt = conn.prepareStatement(bufSql.toString());
			pstmt.setInt(1, num);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
			rest = pstmt.executeQuery();
			String[] columnNames=getColumnName(rest);
			TestVO userInfo;
			while (rest.next()) {
				userInfo=new TestVO();
				userInfo.setCITY_CODE(rest.getString("CITY_CODE"));
				userInfo.setCREATE_DATE(rest.getString("CREATE_DATE"));
				userInfo.setCUSTOMER_ID(rest.getString("CUSTOMER_ID"));
				userInfo.setFIRST_NAME(rest.getString("FIRST_NAME"));
				userInfo.setIDENTITY_CODE(rest.getString("IDENTITY_CODE"));
				userInfo.setRN(rest.getString("RN"));
				resultList.add(userInfo);
			}

		} catch (Exception e) {
			logger.error( e);
		}finally{
			close(rest,pstmt,conn);
		}
		
		return resultList;

	}
	
	
	
	// 无临时物理文件 单xls 单sheet
	public void getAllUserInfo1(int num,OutputStream outputStream){
		StringBuffer bufSql = new StringBuffer();
		bufSql.append("select rownum,CUSTOMER_ID,FIRST_NAME,CITY_CODE");
		bufSql.append(" from bb_customer_info_t where rownum<=?");
		
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		
		int sheetNum=1;
		
		try {

			conn = getConnection();
			pstmt = conn.prepareStatement(bufSql.toString());
			pstmt.setInt(1, num);
			rest = pstmt.executeQuery();

			WritableWorkbook workbook = Workbook.createWorkbook(outputStream);	
			WritableSheet sheet=createSheet(workbook,sheetNum);

			Label labelName1 = new Label (0,0,"序号");
			Label labelName2 = new Label (1,0,"客户编号");
			Label labelName3 = new Label (2,0,"客户姓名");
			Label labelName4 = new Label (2,0,"所属地市");

			sheet.addCell(labelName1); 
			sheet.addCell(labelName2); 
			sheet.addCell(labelName3); 
			sheet.addCell(labelName4);
			
			int rowNum=0;
			while (rest.next()) {
				Label labell = new Label (0,rowNum+1,rest.getString("rownum"));
				Label label2 = new Label (1,rowNum+1,rest.getString("CUSTOMER_ID"));
				Label label3 = new Label (2,rowNum+1,rest.getString("FIRST_NAME") );
				Label label4 = new Label (2,rowNum+1,rest.getString("CITY_CODE") );
				sheet.addCell(labell); 
				sheet.addCell(label2); 
				sheet.addCell(label3); 
				sheet.addCell(label4);
				rowNum++;
			}
			workbook.write();
			workbook.close();

			
		} catch (Exception e) {
			logger.error( e);
		}finally{
			close(rest,pstmt,conn);
		}

	}
	
	// 无临时物理文件 单xls 单sheet
	public void getAllUserInfo2(int num,OutputStream outputStream){
		StringBuffer bufSql = new StringBuffer();
		bufSql.append("select rownum,CUSTOMER_ID,FIRST_NAME,CITY_CODE ");
		bufSql.append(" from bb_customer_info_t where rownum<=?");
		
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		
		int sheetNum=1;
		
		try {

			conn = getConnection();
			pstmt = conn.prepareStatement(bufSql.toString());
			pstmt.setInt(1, num);
			rest = pstmt.executeQuery();

			WritableWorkbook workbook = Workbook.createWorkbook(outputStream);	
			int rowNum=1;
			WritableSheet sheet=createSheet(workbook,sheetNum);
			String[] titles=new String[]{"序号","客户编号","客户姓名","所属地市"};
			buildExcelHeader(sheet,titles);
			while (rest.next()) {
				buildExcelRow(sheet,rowNum++,rest);
			}
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			logger.error( e);
		}finally{
			close(rest,pstmt,conn);
		}

	}
	
	

	
	//无临时物理文件 单xls 多sheet
	public void getAllUserInfo3(int num,OutputStream outputStream){
		StringBuffer bufSql = new StringBuffer();
		bufSql.append("select rownum,CUSTOMER_ID,FIRST_NAME,CITY_CODE ");
		bufSql.append(" from bb_customer_info_t where rownum<=?");
		
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		
		int sheetNum=1;
		
		try {

			conn = getConnection();
			pstmt = conn.prepareStatement(bufSql.toString());
			pstmt.setInt(1, num);
			rest = pstmt.executeQuery();
			String[] columnName=getColumnName(rest);
			
			WritableWorkbook workbook = Workbook.createWorkbook(outputStream);		

			int rowNum=1;
			WritableSheet sheet=createSheet(workbook,sheetNum);
			String[] titles=new String[]{"序号","客户编号","客户姓名"};
			buildExcelHeader(sheet,titles);
			while (rest.next()) {
				buildExcelRow(sheet,rowNum++,rest);
				if (rowNum>sheetSize){
					rowNum=1;
					sheet=createSheet(workbook,++sheetNum);
					buildExcelHeader(sheet,titles);
				}
			}
			workbook.write();
			workbook.close();
			
		} catch (Exception e) {
			logger.error( e);
		}finally{
			close(rest,pstmt,conn);
		}

	}
	

	
	
	//无临时物理文件 单xls 多sheet
	public void getAllUserInfo4(int num,OutputStream outputStream){
		StringBuffer bufSql = new StringBuffer();
		bufSql.append("select rownum,CUSTOMER_ID,FIRST_NAME");
		bufSql.append(" from bb_customer_info_t where rownum<=?");
		
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(bufSql.toString());
			pstmt.setInt(1, num);
			rest = pstmt.executeQuery();
			String[] titles=new String[]{"序号","客户编号","客户姓名","所属地市"};
			outputXLS(rest,outputStream,titles,null);
			
		} catch (Exception e) {
			logger.error( e);
		}finally{
			close(rest,pstmt,conn);
		}

	}
	
	
	
	// 无临时物理文件 单CSV
	
	public void getAllUserInfoCSV(int num,OutputStream outputStream){
		StringBuffer bufSql = new StringBuffer();
		bufSql.append("select rownum RN,CUSTOMER_ID,FIRST_NAME,CITY_CODE ");
		bufSql.append(" from bb_customer_info_t where rownum<=?");
		
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		
		int sheetNum=1;
		
		try {

			conn = getConnection();
			pstmt = conn.prepareStatement(bufSql.toString());
			pstmt.setInt(1, num);
			rest = pstmt.executeQuery();


			String[] titles=new String[]{"序号","客户编号","客户姓名","所属地市"};
			DataAccessUtil.outputCSV(rest,outputStream,titles,new HashMap());

		} catch (Exception e) {
			logger.error( e);
		}finally{
			close(rest,pstmt,conn);
		}

	}
	
	public List getSomeUserInfo_a(int num,int startRow,int endRow){
		StringBuffer bufSql = new StringBuffer();
		bufSql.append("select * from (select rownum rownumt,CUSTOMER_ID,FIRST_NAME,CITY_CODE ");
		bufSql.append(" from bb_customer_info_t where rownum<? ) where rownumt>=? ");
		
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		List userList=new ArrayList();
		
		try {
			conn = getConnection();
			pstmt = ConnectionUtils.prepareStatement(conn,bufSql.toString());
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);
			rest = pstmt.executeQuery();
			
			String[] columnName=getColumnName(rest);

			Map userInfo=null;
			while (rest.next()) {
				userInfo=new HashMap();
				buildRecord(rest,columnName,userInfo);
				userList.add(userInfo);
			}
		} catch (Exception e) {
			LogHandler.errorLog(logger, e);
			userList=null;
		}finally{
			close(rest, pstmt, conn);
		}
		
		return userList;
	}
	
	public void getAllUserInfo_a(int num,OutputStream outputStream){
		StringBuffer bufSql = new StringBuffer();
		bufSql.append("select rownum,CUSTOMER_ID,FIRST_NAME,CITY_CODE ");
		bufSql.append(" from bb_customer_info_t where rownum<=?");
		
		Connection conn=null;
		PreparedStatement pstmt = null;
		ResultSet rest = null;
		
		int sheetNum=1;
		
		try {

			conn = getConnection();
			pstmt = conn.prepareStatement(bufSql.toString());
			pstmt.setInt(1, num);
			rest = pstmt.executeQuery();

			
		} catch (Exception e) {
			logger.error( e);
		}finally{
			close(rest,pstmt,conn);
		}

	}
	
	public void getAllUserInfo(int num,OutputStream outputStream){
		getAllUserInfo3(num,outputStream);
	}
	
	
	
	/////////////////////////////////////////////////////////////////////////
	
	public static void buildExcelHeader(WritableSheet sheet, String[] headerName) throws RowsExceededException, WriteException{
		for (short i=0;i<headerName.length;i++){
			Label label = new Label (i,0,headerName[i]);
			sheet.addCell(label); 
		}
	}

	public static void buildExcelRow(WritableSheet sheet, int rowNum,ResultSet resultSet) throws RowsExceededException, WriteException, SQLException{
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols = metaData.getColumnCount();
		for (short i=1;i<=cols;i++){
			Label label = new Label (i-1,rowNum,resultSet.getString(i));
			sheet.addCell(label); 
		}
	}
	
	public static WritableSheet createSheet(WritableWorkbook workbook,int sheetNum){
		WritableSheet sheet = workbook.createSheet("Sheet "+sheetNum,sheetNum-1);
		return sheet;
	}
	
	public static void outputXLS(ResultSet rest,OutputStream outputStream,String[] titles ,Map mappingItems){
		int sheetSizeT=10000;
		try {
			if (mappingItems==null){
				mappingItems=new HashMap();
			}
			String[] columnNames= getColumnName(rest);
			int colNum=columnNames.length;
			int rowNum=1;
			int sheetNum=1;
			
			WritableWorkbook workbook = Workbook.createWorkbook(outputStream);	
			
			WritableSheet sheet=createSheet(workbook,sheetNum);
			buildExcelHeader(sheet,titles);

			while (rest.next()) {
				for (int i=0;i<colNum;i++){
//					String value=rest.getString(i);
//					Map mappingItem=(Map)mappingItems.get(columnNames[i]);
//					if (mappingItem!=null){
//						value=convertString(mappingItem.get(value),null);
//					}
					Label label = new Label (i,rowNum++,rest.getString(i));
					sheet.addCell(label);
//					value=null;
				}
				if (rowNum>sheetSizeT){
					rowNum=1;
					sheet=createSheet(workbook,++sheetNum);
					buildExcelHeader(sheet,titles);
				}
			}
			
			workbook.write();
			workbook.close();
		} catch (Exception e) {
			logger.error( e);
		}
	}
	
	public static String convertString(Object obj,String nullTo){
		return obj==null?nullTo:obj.toString();
	}
}
