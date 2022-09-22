package model.services;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.entities.Order;

public class ReportPDF {

	private Document document;
	
	private OutputStream file;
	
	public ReportPDF(Document document, OutputStream file) throws MalformedURLException, IOException, DocumentException {
		this.document = document;
		this.file 	  = file;
	}
	
	public void generate(List<Order> list) throws MalformedURLException, IOException, DocumentException {
		report(list);
	}
	
	private void report(List<Order> list) throws MalformedURLException, IOException, DocumentException {
		
		PdfWriter.getInstance(document, file);
		
		Image image = Image.getInstance("report.png");// Header Image
		image.scaleAbsolute(580f, 120f);// image width,height

		PdfPTable irdTable = new PdfPTable(2);
		irdTable.addCell(getIRDCell("Total"));
		irdTable.addCell(getIRDCell("Consulta"));
		Double totalValue = 0.0;
		for(Order o: list) {totalValue += Double.parseDouble(o.getTotal().replace(",", "."));}
		irdTable.addCell(getIRDCell(String.format("%.2f", totalValue))); 
		String dateNow = DateTimeFormatter.ofPattern("dd/MM/yyyy").format(LocalDate.now());
		irdTable.addCell(getIRDCell(dateNow)); 

		PdfPTable irhTable = new PdfPTable(3);
		irhTable.setWidthPercentage(100);

		irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
		irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
		irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
		irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
		irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
		PdfPCell invoiceTable = new PdfPCell(irdTable);
		invoiceTable.setBorder(0);
		irhTable.addCell(invoiceTable);


		PdfPTable billTable = new PdfPTable(5); 
		billTable.setWidthPercentage(100);
		billTable.setWidths(new float[] { 1, 3, 4, 2, 3 });
		billTable.setSpacingBefore(30.0f);
		billTable.addCell(getBillHeaderCell("Cód."));
		billTable.addCell(getBillHeaderCell("Telefone"));
		billTable.addCell(getBillHeaderCell("Data"));
		billTable.addCell(getBillHeaderCell("Valor"));
		billTable.addCell(getBillHeaderCell("Cliente"));

		for(int i = 0; i < list.size(); i ++) {
			billTable.addCell(getBillRowCell(String.valueOf(list.get(i).getOrderCode())));
			billTable.addCell(getBillRowCell(list.get(i).getOrderClient().getPhone()));
			billTable.addCell(getBillRowCell(list.get(i).getDate()));
			billTable.addCell(getBillRowCell(list.get(i).getTotal()));
			billTable.addCell(getBillRowCell(list.get(i).getOrderClient().getName()));
		}


		PdfPCell summaryL = new PdfPCell();
		summaryL.setColspan(3);
		billTable.addCell(summaryL);

		PdfPCell summaryR = new PdfPCell();
		summaryR.setColspan(3);
		billTable.addCell(summaryR);
		
		document.setPageSize(PageSize.A4);
		document.open();
		
		document.add(image);
		document.add(irhTable);
		document.add(billTable);
				
		document.close();
		file.close();
	}

	public static PdfPCell getIRHCell(String text, int alignment) {
		FontSelector fs = new FontSelector();
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 16);
		/* font.setColor(BaseColor.GRAY); */
		fs.addFont(font);
		Phrase phrase = fs.process(text);
		PdfPCell cell = new PdfPCell(phrase);
		cell.setPadding(5);
		cell.setHorizontalAlignment(alignment);
		cell.setBorder(PdfPCell.NO_BORDER);
		return cell;
	}

	public static PdfPCell getIRDCell(String text) {
		PdfPCell cell = new PdfPCell(new Paragraph(text));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(5.0f);
		cell.setBorderColor(BaseColor.LIGHT_GRAY);
		return cell;
	}

	public static PdfPCell getBillHeaderCell(String text) {
		FontSelector fs = new FontSelector();
		Font font = FontFactory.getFont(FontFactory.HELVETICA, 11);
		font.setColor(BaseColor.GRAY);
		fs.addFont(font);
		Phrase phrase = fs.process(text);
		PdfPCell cell = new PdfPCell(phrase);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(5.0f);
		return cell;
	}

	public static PdfPCell getBillRowCell(String text) {
		PdfPCell cell = new PdfPCell(new Paragraph(text));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(5.0f);
		cell.setBorderWidthBottom(0);
		cell.setBorderWidthTop(0);
		return cell;
	}


}
