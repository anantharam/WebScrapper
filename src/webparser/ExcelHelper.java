package webparser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelHelper {

	public static void writeExcel(List<CompanyModel> listBook, String excelFilePath) throws IOException {
	    Workbook workbook = new HSSFWorkbook();
	    Sheet sheet = workbook.createSheet();	
	    
	    int rowCount = 0;	 
	    for (CompanyModel aRestInfo : listBook) {
	        Row row = sheet.createRow(rowCount++);
	       //System.out.println(aRestInfo.getName());
	        writeBook(aRestInfo, row, workbook,excelFilePath);
	        for(int colNum = 0; colNum<row.getLastCellNum();colNum++)   
	        	sheet.autoSizeColumn(colNum);
	    }	 
	    try {
	    	FileOutputStream outputStream = new FileOutputStream(excelFilePath);
	        workbook.write(outputStream);
	    }catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private static void writeBook(CompanyModel companyModel, Row row, Workbook workbook, String folderName) throws IOException {
	    Cell cell = row.createCell(1);
	    cell.setCellValue(companyModel.getName());
	 
	    cell = row.createCell(2);
		cell.setCellValue(companyModel.getAddress());
		
		cell = row.createCell(3);
		cell.setCellValue(companyModel.getLandmark());

		cell = row.createCell(4);
		cell.setCellValue(companyModel.getPincode());
	    
	    cell = row.createCell(5);
	    cell.setCellValue(companyModel.getRatingValue());
	    
	    cell = row.createCell(6);
	    cell.setCellValue(companyModel.getRating());
	    
	    cell = row.createCell(7);
	    cell.setCellValue(companyModel.getYear());
	    
	    cell = row.createCell(8);
	    cell.setCellValue(companyModel.getHoursOfOperation());
	    //to enable newlines you need set a cell styles with wrap=true
	    CellStyle cs = workbook.createCellStyle();
	    cs.setWrapText(true);
	    cell.setCellStyle(cs);
	    
	    cell = row.createCell(9);
		List<String> tags = companyModel.getTags();
		if(tags!=null && tags.size()>0) {
			String stringTags = String.join(",", tags);
			cell.setCellValue(stringTags);
		}
		
	    cell = row.createCell(10);
		cell.setCellValue(companyModel.getMoreInfoLink());
		
		int count = 11;
		for(String phoneNumber : companyModel.getPhone()) {
			cell = row.createCell(count++);
		    cell.setCellValue(phoneNumber);
		}
		
//		int count = 10;
//		for(String photoPath : companyModel.getPhotos()) {
//			cell = row.createCell(count++);
//			cell.setCellValue(photoPath);
//			FileOutputStream out = null;
//			
//			try {
//				//write the actual photo
//				String outputFolder = folderName.substring(0, folderName.length()-3);
//				String photoName = photoPath.substring(photoPath.lastIndexOf("\\")+1, photoPath.length());
//				Response resultImageResponse = Jsoup.connect(photoPath).ignoreContentType(true).execute();
//				out = (new FileOutputStream(new java.io.File(outputFolder + photoName)));
//				out.write(resultImageResponse.bodyAsBytes());// resultImageResponse.body() is where the image's contents are.
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			} finally {
//				out.close();
//			}
//			
//		}

	}
}
