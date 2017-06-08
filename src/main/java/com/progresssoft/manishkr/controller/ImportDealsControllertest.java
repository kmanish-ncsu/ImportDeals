package com.progresssoft.manishkr.controller;

import com.progresssoft.manishkr.exception.FileAlreadyProcessedException;
import com.progresssoft.manishkr.exception.FileMoveException;
import com.progresssoft.manishkr.exception.FileParseException;
import com.progresssoft.manishkr.model.DealCount;
import com.progresssoft.manishkr.model.DealSourceFile;
import com.progresssoft.manishkr.service.ImportDealsService;
import com.progresssoft.manishkr.util.FileUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ImportDealsControllertest {

    @InjectMocks
    ImportDealsController cut;

    @Mock
    private ImportDealsService importDealsService;

    @Mock
    private FileUtil fileUtil;

    @Mock
    RedirectAttributes redirectAttributes;

    @Before
    public void setup() {

    }

    @Test
    public void testAllNullModelMap() {
        ModelAndView result = cut.all("DUMMY");

        Assert.assertNotNull(result);
        Assert.assertEquals("all",result.getViewName());
        Assert.assertEquals(null,result.getModel().get("unprocessedSourceFiles"));
        Assert.assertEquals(null,result.getModel().get("processedSourceFiles"));
    }

    @Test
    public void testAll() {
        List<String> unprocessedFiles = new ArrayList<>();
        unprocessedFiles.add("file1");
        unprocessedFiles.add("file2");
        when(fileUtil.getUnprocessedFiles()).thenReturn(unprocessedFiles);

        List<String> processedFiles = new ArrayList<>();
        processedFiles.add("file3");
        when(fileUtil.getProcessedFiles()).thenReturn(processedFiles);

        ModelAndView result = cut.all("DUMMY");

        Assert.assertNotNull(result);
        Assert.assertEquals("all",result.getViewName());
        Assert.assertNotNull(result.getModel().get("unprocessedSourceFiles"));
        Assert.assertNotNull(result.getModel().get("processedSourceFiles"));
    }

    @Test
    public void testprocess(){
        ModelAndView result = cut.all("DUMMY");
        Assert.assertNotNull(result);

    }

    @Test
    public void testprocessFileAlreadyProcessedException() throws FileAlreadyProcessedException, FileMoveException, FileParseException {
        doThrow(new FileAlreadyProcessedException()).when(importDealsService).processFile(anyString());
        ModelAndView result = cut.process("DUMMY", redirectAttributes);
        Assert.assertNotNull(result);
        Assert.assertEquals("redirect:/importdeals/all",result.getViewName());
        verify(redirectAttributes).addFlashAttribute("fileToProcessMsg","DUMMY already processed!");
    }

    @Test
    public void testprocessFileParseException() throws FileAlreadyProcessedException, FileMoveException, FileParseException {
        doThrow(new FileParseException()).when(importDealsService).processFile(anyString());
        ModelAndView result = cut.process("DUMMY", redirectAttributes);
        Assert.assertNotNull(result);
        Assert.assertEquals("redirect:/importdeals/all",result.getViewName());
        verify(redirectAttributes).addFlashAttribute("fileToProcessMsg","DUMMY could not be processed!");

    }

    @Test
    public void testprocessFileMoveException() throws FileAlreadyProcessedException, FileMoveException, FileParseException {
        doThrow(new FileMoveException()).when(importDealsService).processFile(anyString());
        ModelAndView result = cut.process("DUMMY", redirectAttributes);
        Assert.assertNotNull(result);
        Assert.assertEquals("redirect:/importdeals/all",result.getViewName());
        verify(redirectAttributes).addFlashAttribute("fileToProcessMsg","DUMMY could not be moved!");

    }

    @Test
    public void testcurrencyCount() throws FileAlreadyProcessedException, FileMoveException, FileParseException {
        List<DealCount> dealCounts = new ArrayList<>();
        dealCounts.add(new DealCount("INR",10));
        dealCounts.add(new DealCount("USD",20));
        dealCounts.add(new DealCount("AED",30));

        doReturn(dealCounts).when(importDealsService).getCurrencyCount();

        ModelAndView result = cut.currencyCount();
        Assert.assertNotNull(result);
        Assert.assertEquals("currencycount",result.getViewName());
        Assert.assertNotNull(result.getModel().get("currencycount"));
    }

    @Test
    public void testfiledetails() throws FileAlreadyProcessedException, FileMoveException, FileParseException {
        DealSourceFile dsf = new DealSourceFile("DUMMY",90,10);

        doReturn(dsf).when(importDealsService).getFileDetails(anyString());

        ModelAndView result = cut.filedetails("DUMMY");
        Assert.assertNotNull(result);
        Assert.assertEquals("filedetails",result.getViewName());
        Assert.assertNotNull(result.getModel().get("filedetails"));

    }

}
