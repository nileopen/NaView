package com.na.naview.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.na.view.NaMuliTextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

public class TextViewActivity extends AppCompatActivity {
    private NaMuliTextView ntvContent;
    private NaMuliTextView ntvContent1;
    private HtmlTextView htmlText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_view);
        ntvContent = (NaMuliTextView) findViewById(R.id.ntvContent);
//        ntvContent.setInitialSizeMultiple(2);
        ntvContent.setText("A sales management or business development position where my strategic " +
                "and consultative selling, cross-cultural relationship building, team facilitation, " +
                "business management, organizational insight, " +
                "and advanced technical skills will be continually challenged. " +
                "I aspire to senior management responsibility and seek a company that embraces growth and change, " +
                "where compensation is performance-based and increased levels of " +
                "responsibility offered those with demonstrated potential.");

        ntvContent1 = (NaMuliTextView) findViewById(R.id.ntvContent1);
//        ntvContent1.setInitialSizeMultiple(3);
        ntvContent1.setText("A sales management or business development position where my strategic " +
//                "and consultative selling, cross-cultural relationship building, team facilitation, " +
//                "business management, organizational insight, " +
//                "and advanced technical skills will be continually challenged. " +
//                "I aspire to senior management responsibility and seek a company that embraces growth and change, " +
//                "where compensation is performance-based and increased levels of " +
                "responsibility offered those with demonstrated potential.");
    }
}
