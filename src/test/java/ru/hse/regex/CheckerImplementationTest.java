package ru.hse.regex;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.stefanbirkner.fishbowl.Fishbowl.exceptionThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by ivanistomin on 10/11/16.
 */
public class CheckerImplementationTest {
    @Test
    public void checkAccordanceNull() throws IllegalArgumentException {
        CheckerImplementation ci = new CheckerImplementation();

        assertTrue(ci.checkAccordance(null, null));

        assertEquals(IllegalArgumentException.class, exceptionThrownBy(()
                -> ci.checkAccordance("test", null)).getClass());

        assertEquals(IllegalArgumentException.class, exceptionThrownBy(()
                -> ci.checkAccordance(null, ci.getEMailPattern())).getClass());
    }

    @Test
    public void checkAccordancePLSQLPattern() {
        CheckerImplementation ci = new CheckerImplementation();

        assertTrue(ci.checkAccordance("fwe234ijad", ci.getPLSQLNamesPattern()));
        assertTrue(ci.checkAccordance("p", ci.getPLSQLNamesPattern()));
        assertFalse(ci.checkAccordance("321test", ci.getPLSQLNamesPattern()));
        assertFalse(ci.checkAccordance("-321test", ci.getPLSQLNamesPattern()));
        assertFalse(ci.checkAccordance("321test-", ci.getPLSQLNamesPattern()));
        assertTrue(ci.checkAccordance("tesalaskd___skdj$$alskdjtd$dfg",
                ci.getPLSQLNamesPattern())); // 30
        assertFalse(ci.checkAccordance("tesalaskdjalskdj$$alskdjtd$df__$$$$asdjalsdkg",
                ci.getPLSQLNamesPattern()));
    }

    @Test
    public void checkAccordanceHTMLLinkPattern() {
        CheckerImplementation ci = new CheckerImplementation();

        assertTrue(ci.checkAccordance("<a href=\"kokrkrdodkrodrkodkrokodr\" id=\"am-b0\" aria-label=\"Подробнее...\" aria-expanded=\"false\" aria-haspopup=\"true\" role=\"button\" jsaction=\"m.tdd;keydown:m.hbke;keypress:m.mskpe\" data-ved=\"0ahUKEwjKysO2lqPQAhWCjCwKHfe3DXkQ7B0IKTAA\">", ci.getHrefURLPattern()));
        assertFalse(ci.checkAccordance("<a class=\"fl\" href=http://webcache.googleu sercontent.com/search?q=cache:fdq2L4O7a5EJ:www.regexplanet.com/simple/+&amp;cd=1&amp;hl=ru&amp;ct=clnk&amp;gl=ru\">", ci.getHrefURLPattern()));
    }

    @Test
    public void checkAccordanceEmailPattern() {
        CheckerImplementation ci = new CheckerImplementation();

        assertTrue(ci.checkAccordance("kwadaw@dawndlaw.ru", ci.getEMailPattern()));
        assertTrue(ci.checkAccordance("kwadawjhg-j.kiuyhgt.uy@dawndlaw.ru.com.ru.org.net", ci.getEMailPattern()));
        assertTrue(ci.checkAccordance("k@dawndlaw.ru", ci.getEMailPattern()));
        assertFalse(ci.checkAccordance("kjjkjjkjjkjjkjjkjjkjjj-kk@askdj.ru", ci.getEMailPattern())); //25
        assertFalse(ci.checkAccordance("--kwadaw@dawndlaw.ru", ci.getEMailPattern()));
        assertFalse(ci.checkAccordance("kwadaw-@dawndlaw.ru", ci.getEMailPattern()));
        assertFalse(ci.checkAccordance("kwadaw.-dawd@dawndlaw.ru", ci.getEMailPattern()));
        assertFalse(ci.checkAccordance("_-akwjdn_adw.dawda-daw@dawkndwa.com.ua", ci.getEMailPattern()));
    }

    @Test
    public void fetchAllTemplatesNull() throws IllegalArgumentException {
        CheckerImplementation ci = new CheckerImplementation();

        // Empty list, because not found anything
        assertTrue(ci.fetchAllTemplates(new StringBuffer("1111"), ci.getPLSQLNamesPattern()).size() == 0);

        // Throws with null
        assertEquals(IllegalArgumentException.class, exceptionThrownBy(()
                -> ci.fetchAllTemplates(new StringBuffer("test"), null)).getClass());

        assertEquals(IllegalArgumentException.class, exceptionThrownBy(()
                -> ci.fetchAllTemplates(null, ci.getEMailPattern())).getClass());

        assertEquals(IllegalArgumentException.class, exceptionThrownBy(()
                -> ci.fetchAllTemplates(null, null)).getClass());
    }

    @Test
    public void fetchAllTemplatesPLSQLPattern() {
        CheckerImplementation ci = new CheckerImplementation();

        StringBuffer sql = new StringBuffer(
                "fwe234ijadljlkj$$iwjowijeow__ioijeowijoijeoiwjoeijwijeoiwjeoiwjoiejejioejoijjjrrr");

        assertTrue(ci.fetchAllTemplates(sql, ci.getPLSQLNamesPattern()).size() == 3);
    }

    @Test
    public void fetchAllTemplatesHTMLLinkPattern() throws IOException {
        CheckerImplementation ci = new CheckerImplementation();

        StringBuffer href = new StringBuffer(
                new String(Files.readAllBytes(
                                Paths.get(getClass().getClassLoader().getResource("test.html").getFile()))));

        assertTrue(ci.fetchAllTemplates(href, ci.getHrefURLPattern()).size() == 10);
    }

    @Test
    public void fetchAllTemplatesEmailPattern() {
        CheckerImplementation ci = new CheckerImplementation();

        StringBuffer mail = new StringBuffer(
                "kjjkjjkjjkjjkjjkjjkjjj-kk@askdj.rukjlkjsdffk3456@askdj.ru-a@ggg.rulkmsfesfse@lkfs.ru");

        assertTrue(ci.fetchAllTemplates(mail, ci.getEMailPattern()).size() == 4);
    }
}