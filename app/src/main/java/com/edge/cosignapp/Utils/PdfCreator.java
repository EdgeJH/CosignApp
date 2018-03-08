package com.edge.cosignapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.edge.cosignapp.Data.WorkContrackData;
import com.edge.cosignapp.R;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user1 on 2017-12-14.
 */

public class PdfCreator {
    Context context;
    String text;
    PdfCallback pdfCallback;

    public PdfCreator(Context context,PdfCallback pdfCallback) {
        this.context = context;
        this.pdfCallback = pdfCallback;
    }

    public void createWorkPdf(WorkContrackData workContrackData) throws IOException, DocumentException {

        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        sdPath += "/Cosign/";

        File file = new File(sdPath);
        file.mkdirs();
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
        file = new File(sdPath, timeStamp + "cosign.pdf");
        OutputStream output = new FileOutputStream(file);

        Document document = new Document(PageSize.A4,40,40,40,40);

        //Step 2
        PdfWriter.getInstance(document, output);

        //Step 3
        document.open();

        BaseFont baseFont= null;
        try {
            InputStream is = context.getAssets().open("fonts/chosunilbo.ttf");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            baseFont = BaseFont.createFont("chosunilbo.ttf", BaseFont.IDENTITY_H, true, false, buffer, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Step 4 Add content
        BaseColor titleColor =  WebColors.getRGBColor("#3F51B5");
        Font paraFont = new Font(baseFont,20f,Font.BOLD,titleColor);
        Paragraph p1 = new Paragraph("근로 계약서",paraFont);
        p1.setAlignment(Paragraph.ALIGN_CENTER);

        //add paragraph to document
        document.add(p1);
        String namingPara = workContrackData.getBusinessData().getCeo() + getString(R.string.naming_1)
                + workContrackData.getPersonalData().getName() +getString(R.string.naming_2);
        Log.d("aaaa", namingPara + ",,");
        Font paraFont2 = new Font(baseFont, 12f, 0, CMYKColor.DARK_GRAY);
        Paragraph p2 = new Paragraph(namingPara,paraFont2);
        p2.setAlignment(Paragraph.ALIGN_LEFT);
        p2.setSpacingBefore(20f);
        p2.setExtraParagraphSpace(100f);
        document.add(p2);

        String workStart = getString(R.string.work_date_1)+workContrackData.getStartDate()+getString(R.string.work_date_2)
                                    +workContrackData.getEndDate()+getString(R.string.work_date_3);
        Paragraph p3 = new Paragraph(workStart,paraFont2);
        p3.setAlignment(Paragraph.ALIGN_LEFT);
        p3.setSpacingBefore(15f);
        p3.setExtraParagraphSpace(100f);
        document.add(p3);

        Paragraph p4 = new Paragraph(getString(R.string.work_date_4),paraFont2);
        p4.setAlignment(Paragraph.ALIGN_LEFT);
        p4.setExtraParagraphSpace(100f);
        p4.setFirstLineIndent(10f);
        document.add(p4);

        String place = getString(R.string.work_place) + workContrackData.getBusinessData().getAddress();
        Paragraph p5 = new Paragraph(place,paraFont2);
        p5.setAlignment(Paragraph.ALIGN_LEFT);
        p5.setSpacingBefore(10f);
        p5.setExtraParagraphSpace(100f);
        document.add(p5);

        String info = getString(R.string.work_info) +workContrackData.getInfo();
        Paragraph p6 = new Paragraph(info,paraFont2);
        p6.setAlignment(Paragraph.ALIGN_LEFT);
        p6.setSpacingBefore(10f);
        p6.setExtraParagraphSpace(100f);
        document.add(p6);

        String workTime = getString(R.string.work_time_1) +workContrackData.getStartTime()
                                    +getString(R.string.work_time_2)+ workContrackData.getEndTime()
                                    +getString(R.string.work_time_3)+workContrackData.getBreakStartTime()
                                    +getString(R.string.work_time_4)+workContrackData.getBreakEndTime()
                                    +getString(R.string.work_time_5);
        Paragraph p7 = new Paragraph(workTime,paraFont2);
        p7.setAlignment(Paragraph.ALIGN_LEFT);
        p7.setSpacingBefore(10f);
        p7.setExtraParagraphSpace(100f);
        document.add(p7);

        String workWeek = getString(R.string.work_week_1)+getString(R.string.wokr_week_2)
                                    +workContrackData.getWorkDay()+getString(R.string.wokr_week_3)
                                    +workContrackData.getHoliday()+context.getString(R.string.wokr_week_4);
        Paragraph p8 = new Paragraph(workWeek,paraFont2);
        p8.setAlignment(Paragraph.ALIGN_LEFT);
        p8.setSpacingBefore(10f);
        p8.setExtraParagraphSpace(100f);
        document.add(p8);

        Paragraph p9 = new Paragraph(getString(R.string.pay_1),paraFont2);
        p9.setAlignment(Paragraph.ALIGN_LEFT);
        p9.setSpacingBefore(10f);
        p9.setExtraParagraphSpace(100f);
        document.add(p9);

        String pay = getString(R.string.pay_5)+workContrackData.getPayWay()
                +"   "+workContrackData.getPay() + getString(R.string.pay_3);
        Paragraph p10 = new Paragraph(pay,paraFont2);
        p10.setAlignment(Paragraph.ALIGN_LEFT);
        p10.setFirstLineIndent(10f);
        p10.setExtraParagraphSpace(100f);
        document.add(p10);
        String bonus="";
        if (!TextUtils.isEmpty(workContrackData.getBonus())){
           bonus  = getString(R.string.pay_5)+getString(R.string.pay_4)
                        +"   "+workContrackData.getBonus();
        }else {
            bonus  = getString(R.string.pay_5)+getString(R.string.pay_4)
                    +"   "+"없음";
        }
        Paragraph p18 = new Paragraph(bonus,paraFont2);
        p18.setAlignment(Paragraph.ALIGN_LEFT);
        p18.setFirstLineIndent(10f);
        p18.setExtraParagraphSpace(100f);
        document.add(p18);


        String method = getString(R.string.pay_6)+workContrackData.getPayMethod();
        Paragraph p11 = new Paragraph(method,paraFont2);
        p11.setAlignment(Paragraph.ALIGN_LEFT);
        p11.setFirstLineIndent(10f);
        p11.setExtraParagraphSpace(100f);
        document.add(p11);

        Paragraph p12 = new Paragraph(getString(R.string.holiday_1),paraFont2);
        p12.setAlignment(Paragraph.ALIGN_LEFT);
        p12.setFirstLineIndent(10f);
        p12.setSpacingBefore(10f);
        p12.setExtraParagraphSpace(100f);
        document.add(p12);

        Paragraph p13 = new Paragraph(getString(R.string.holiday_2),paraFont2);
        p13.setAlignment(Paragraph.ALIGN_LEFT);
        p13.setFirstLineIndent(10f);
        p13.setExtraParagraphSpace(100f);
        document.add(p13);

        Paragraph p14 = new Paragraph(getString(R.string.send_1),paraFont2);
        p14.setAlignment(Paragraph.ALIGN_LEFT);
        p14.setFirstLineIndent(10f);
        p14.setSpacingBefore(10f);
        p14.setExtraParagraphSpace(100f);
        document.add(p14);

        Paragraph p15 = new Paragraph(getString(R.string.send_2),paraFont2);
        p15.setAlignment(Paragraph.ALIGN_LEFT);
        p15.setFirstLineIndent(10f);
        p15.setExtraParagraphSpace(100f);
        document.add(p15);
        Paragraph p16 = new Paragraph(getString(R.string.etc_1),paraFont2);
        p16.setAlignment(Paragraph.ALIGN_LEFT);
        p16.setFirstLineIndent(10f);
        p16.setSpacingBefore(10f);
        p16.setExtraParagraphSpace(100f);
        document.add(p16);

        Paragraph p17 = new Paragraph(getString(R.string.etc_2),paraFont2);
        p17.setAlignment(Paragraph.ALIGN_LEFT);
        p17.setFirstLineIndent(10f);
        p17.setExtraParagraphSpace(100f);
        document.add(p17);


        Font paraFont3 = new Font(baseFont,16f,Font.BOLD,CMYKColor.BLACK);


        Paragraph p19 = new Paragraph(workContrackData.getContDate(),paraFont3);
        p19.setAlignment(Paragraph.ALIGN_CENTER);
        p19.setSpacingBefore(30f);
        p19.setExtraParagraphSpace(100f);
        document.add(p19);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = workContrackData.getSignature();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100 , stream);
        Image myImg = Image.getInstance(stream.toByteArray());
        float size = document.getPageSize().getWidth()/20;
        myImg.scaleAbsolute(size,size);
        myImg.setAlignment(Image.MIDDLE);
        myImg.setAlignment(Image.ALIGN_CENTER|Image.TEXTWRAP);

        PdfPCell cellSign1 = new PdfPCell();
        cellSign1.setImage(myImg);
        cellSign1.setBorder(Rectangle.NO_BORDER);
        cellSign1.setRowspan(3);

        PdfPCell cellSign2 = new PdfPCell();
       // cellSign2.setImage(myImg);
        cellSign2.setBorder(Rectangle.NO_BORDER);
        cellSign2.setRowspan(3);

        String company = getString(R.string.sign_1)+workContrackData.getBusinessData().getCompany();
        Paragraph p20 =new Paragraph(company,paraFont2);
        p20.setAlignment(Paragraph.ALIGN_LEFT);
        PdfPCell cell2 = new PdfPCell();
        cell2.addElement(p20);
        cell2.setBorder(Rectangle.NO_BORDER);

        String address = getString(R.string.sign_2) + workContrackData.getBusinessData().getAddress();
        Paragraph p21  = new Paragraph(address,paraFont2);
        p20.setAlignment(Paragraph.ALIGN_LEFT);
        PdfPCell cell3 = new PdfPCell();
        cell3.addElement(p21);
        cell3.setBorder(Rectangle.NO_BORDER);

        String ceo = getString(R.string.sign_3)+workContrackData.getBusinessData().getCeo();
        Paragraph p22 = new Paragraph(ceo,paraFont2);
        p22.setAlignment(Paragraph.ALIGN_LEFT);
        PdfPCell cell4 = new PdfPCell();
        cell4.addElement(p22);
        cell4.setBorder(Rectangle.NO_BORDER);

        String perAddress = getString(R.string.sign_4)+workContrackData.getPersonalData().getAddress();
        Paragraph p23 =new Paragraph(perAddress,paraFont2);
        p20.setAlignment(Paragraph.ALIGN_LEFT);
        PdfPCell cell5 = new PdfPCell();
        cell5.addElement(p23);
        cell5.setBorder(Rectangle.NO_BORDER);

        String phone = getString(R.string.sign_5) + workContrackData.getPersonalData().getPhoneNumber();
        Paragraph p24  = new Paragraph(phone,paraFont2);
        p20.setAlignment(Paragraph.ALIGN_LEFT);
        PdfPCell cell6 = new PdfPCell();
        cell6.addElement(p24);
        cell6.setBorder(Rectangle.NO_BORDER);

        String name = getString(R.string.sign_6)+workContrackData.getPersonalData().getName();
        Paragraph p25 = new Paragraph(name,paraFont2);
        p22.setAlignment(Paragraph.ALIGN_LEFT);
        PdfPCell cell7 = new PdfPCell();
        cell7.addElement(p25);
        cell7.setBorder(Rectangle.NO_BORDER);

        float[] columnWidths = {5, 1};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table.addCell(cell2);
        table.addCell(cellSign1);
        table.addCell(cell3);
        table.addCell(cell4);
        table.setSpacingBefore(20f);
        table.setWidthPercentage(60);
        document.add(table);

        PdfPTable table2 = new PdfPTable(columnWidths);
        table2.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
        table2.addCell(cell5);
        table2.addCell(cellSign2);
        table2.addCell(cell6);
        table2.addCell(cell7);
        table2.setSpacingBefore(10f);
        table2.setWidthPercentage(60);
        document.add(table2);
        //Step 5: Close the document
        Log.d("test123","aaaaaaa");

        document.close();
        output.close();
        pdfCallback.result(200,file.getAbsolutePath());

    }

    public String getString (int resId){
        return context.getString(resId);
    }
}
