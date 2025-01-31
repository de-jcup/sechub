// SPDX-License-Identifier: MIT
package com.mercedesbenz.sechub.domain.scan.product.sereco;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.mercedesbenz.sechub.commons.model.ScanType;
import com.mercedesbenz.sechub.domain.scan.project.FalsePositiveProjectData;
import com.mercedesbenz.sechub.domain.scan.project.WebscanFalsePositiveProjectData;
import com.mercedesbenz.sechub.sereco.metadata.SerecoVulnerability;
import com.mercedesbenz.sechub.sereco.metadata.SerecoWeb;
import com.mercedesbenz.sechub.sereco.metadata.SerecoWebRequest;

class WebScanProjectDataFalsePositiveStrategyTest {

    private static final String PROTOCOL1 = "https";
    private static final String METHOD1 = "GET";
    private static final String TARGET1 = "https://api.example.com/rest/users/projects";
    private static final int CWE_ID_4711 = 4711;

    private WebScanProjectDataFalsePositiveStrategy strategyToTest;

    private static final SerecoProjectDataWebScanFalsePositiveSupport webscanFalsePositiveProjectDataSupport = mock();

    @BeforeEach
    void beforeEach() {
        Mockito.reset(webscanFalsePositiveProjectDataSupport);

        strategyToTest = new WebScanProjectDataFalsePositiveStrategy(webscanFalsePositiveProjectDataSupport);
    }

    @Test
    void patternMap_empty_results_in_result_being_false() {
        /* prepare */
        FalsePositiveTestDataContainer testDataContainer = createInitialTestDataWithMatchingVulnerabilityAndFalsePositiveDefinition();

        /* execute */
        boolean isFalsePositive = strategyToTest.isFalsePositive(testDataContainer.vulnerability, testDataContainer.projectData, new HashMap<>());

        /* test */
        assertFalse(isFalsePositive);
    }

    @Test
    void scantype_is_not_webscan_results_in_result_being_false() {
        /* prepare */
        Map<String, Pattern> projectDataPatternMap = new HashMap<>();
        projectDataPatternMap.put("key", mock(Pattern.class));

        SerecoVulnerability vulnerability = mock(SerecoVulnerability.class);
        FalsePositiveProjectData projectData = mock(FalsePositiveProjectData.class);

        when(vulnerability.getScanType()).thenReturn(null);

        /* execute */
        boolean isFalsePositive = strategyToTest.isFalsePositive(vulnerability, projectData, projectDataPatternMap);

        /* test */
        verify(vulnerability, times(1)).getScanType();
        verify(projectData, never()).getWebScan();
        assertFalse(isFalsePositive);
    }

    @Test
    void projectData_webscan_is_null_results_in_result_being_false() {
        /* prepare */
        Map<String, Pattern> projectDataPatternMap = new HashMap<>();
        projectDataPatternMap.put("key", mock(Pattern.class));

        SerecoVulnerability vulnerability = mock(SerecoVulnerability.class);
        FalsePositiveProjectData projectData = mock(FalsePositiveProjectData.class);

        when(vulnerability.getScanType()).thenReturn(ScanType.WEB_SCAN);
        when(projectData.getWebScan()).thenReturn(null);

        /* execute */
        boolean isFalsePositive = strategyToTest.isFalsePositive(vulnerability, projectData, projectDataPatternMap);

        /* test */
        verify(vulnerability, times(1)).getScanType();
        verify(projectData, times(1)).getWebScan();
        assertFalse(isFalsePositive);
    }

    @Test
    void vulnerability_web_part_is_null_results_in_result_being_false() {
        /* prepare */
        Map<String, Pattern> projectDataPatternMap = new HashMap<>();
        projectDataPatternMap.put("key", mock(Pattern.class));

        SerecoVulnerability vulnerability = mock(SerecoVulnerability.class);
        FalsePositiveProjectData projectData = mock(FalsePositiveProjectData.class);

        when(vulnerability.getScanType()).thenReturn(ScanType.WEB_SCAN);
        when(vulnerability.getWeb()).thenReturn(null);
        when(projectData.getWebScan()).thenReturn(new WebscanFalsePositiveProjectData());

        /* execute */
        boolean isFalsePositive = strategyToTest.isFalsePositive(vulnerability, projectData, projectDataPatternMap);

        /* test */
        verify(vulnerability, times(1)).getScanType();
        verify(projectData, times(1)).getWebScan();
        verify(vulnerability, times(1)).getWeb();
        assertFalse(isFalsePositive);
    }

    @Test
    void cwe_comparison_fails_results_in_result_being_false() {
        /* prepare */
        FalsePositiveTestDataContainer testDataContainer = createInitialTestDataWithMatchingVulnerabilityAndFalsePositiveDefinition();

        when(webscanFalsePositiveProjectDataSupport.areBothHavingSameCweIdOrBothNoCweId(testDataContainer.projectData.getWebScan(),
                testDataContainer.vulnerability)).thenReturn(false);

        /* execute */
        boolean isFalsePositive = strategyToTest.isFalsePositive(testDataContainer.vulnerability, testDataContainer.projectData,
                testDataContainer.projectDataPatternMap);

        /* test */
        verify(webscanFalsePositiveProjectDataSupport, times(1)).areBothHavingSameCweIdOrBothNoCweId(testDataContainer.projectData.getWebScan(),
                testDataContainer.vulnerability);
        // nothing else is called
        verify(webscanFalsePositiveProjectDataSupport, never()).isMatchingUrlPattern(any(), any());
        verify(webscanFalsePositiveProjectDataSupport, never()).isMatchingMethodOrIgnoreIfNotSet(any(), any());

        assertFalse(isFalsePositive);
    }

    @Test
    void url_pattern_comparison_fails_results_in_result_being_false() {
        /* prepare */
        FalsePositiveTestDataContainer testDataContainer = createInitialTestDataWithMatchingVulnerabilityAndFalsePositiveDefinition();

        WebscanFalsePositiveProjectData webScan = testDataContainer.projectData.getWebScan();
        when(webscanFalsePositiveProjectDataSupport.areBothHavingSameCweIdOrBothNoCweId(webScan, testDataContainer.vulnerability)).thenReturn(true);

        when(webscanFalsePositiveProjectDataSupport.isMatchingUrlPattern(TARGET1, testDataContainer.projectDataPatternMap)).thenReturn(false);

        /* execute */
        boolean isFalsePositive = strategyToTest.isFalsePositive(testDataContainer.vulnerability, testDataContainer.projectData,
                testDataContainer.projectDataPatternMap);

        /* test */
        verify(webscanFalsePositiveProjectDataSupport, times(1)).areBothHavingSameCweIdOrBothNoCweId(webScan, testDataContainer.vulnerability);
        verify(webscanFalsePositiveProjectDataSupport, times(1)).isMatchingUrlPattern(TARGET1, testDataContainer.projectDataPatternMap);
        // nothing else is called
        verify(webscanFalsePositiveProjectDataSupport, never()).isMatchingMethodOrIgnoreIfNotSet(any(), any());

        assertFalse(isFalsePositive);
    }

    @Test
    void method_comparison_fails_results_in_result_being_false() {
        /* prepare */
        FalsePositiveTestDataContainer testDataContainer = createInitialTestDataWithMatchingVulnerabilityAndFalsePositiveDefinition();

        WebscanFalsePositiveProjectData webScan = testDataContainer.projectData.getWebScan();
        when(webscanFalsePositiveProjectDataSupport.areBothHavingSameCweIdOrBothNoCweId(webScan, testDataContainer.vulnerability)).thenReturn(true);

        when(webscanFalsePositiveProjectDataSupport.isMatchingUrlPattern(TARGET1, testDataContainer.projectDataPatternMap)).thenReturn(true);

        when(webscanFalsePositiveProjectDataSupport.isMatchingMethodOrIgnoreIfNotSet(METHOD1, webScan.getMethods())).thenReturn(false);

        /* execute */
        boolean isFalsePositive = strategyToTest.isFalsePositive(testDataContainer.vulnerability, testDataContainer.projectData,
                testDataContainer.projectDataPatternMap);

        /* test */
        verify(webscanFalsePositiveProjectDataSupport, times(1)).areBothHavingSameCweIdOrBothNoCweId(webScan, testDataContainer.vulnerability);
        verify(webscanFalsePositiveProjectDataSupport, times(1)).isMatchingUrlPattern(TARGET1, testDataContainer.projectDataPatternMap);
        verify(webscanFalsePositiveProjectDataSupport, times(1)).isMatchingMethodOrIgnoreIfNotSet(METHOD1, webScan.getMethods());

        assertFalse(isFalsePositive);
    }

    @Test
    void all_conditions_are_satisfied_results_in_result_being_true() {
        /* prepare */
        FalsePositiveTestDataContainer testDataContainer = createInitialTestDataWithMatchingVulnerabilityAndFalsePositiveDefinition();

        WebscanFalsePositiveProjectData webScan = testDataContainer.projectData.getWebScan();
        when(webscanFalsePositiveProjectDataSupport.areBothHavingSameCweIdOrBothNoCweId(webScan, testDataContainer.vulnerability)).thenReturn(true);

        when(webscanFalsePositiveProjectDataSupport.isMatchingUrlPattern(TARGET1, testDataContainer.projectDataPatternMap)).thenReturn(true);

        when(webscanFalsePositiveProjectDataSupport.isMatchingMethodOrIgnoreIfNotSet(METHOD1, webScan.getMethods())).thenReturn(true);

        /* execute */
        boolean isFalsePositive = strategyToTest.isFalsePositive(testDataContainer.vulnerability, testDataContainer.projectData,
                testDataContainer.projectDataPatternMap);

        /* test */
        verify(webscanFalsePositiveProjectDataSupport, times(1)).areBothHavingSameCweIdOrBothNoCweId(webScan, testDataContainer.vulnerability);
        verify(webscanFalsePositiveProjectDataSupport, times(1)).isMatchingUrlPattern(TARGET1, testDataContainer.projectDataPatternMap);
        verify(webscanFalsePositiveProjectDataSupport, times(1)).isMatchingMethodOrIgnoreIfNotSet(METHOD1, webScan.getMethods());

        assertTrue(isFalsePositive);
    }

    private FalsePositiveTestDataContainer createInitialTestDataWithMatchingVulnerabilityAndFalsePositiveDefinition() {
        return new FalsePositiveTestDataContainer();
    }

    private class FalsePositiveTestDataContainer {
        FalsePositiveProjectData projectData = createValidTestFalsePositiveProjectData();
        SerecoVulnerability vulnerability = createValidTestVulnerability();
        Map<String, Pattern> projectDataPatternMap = createMockedTestMap();

    }

    private FalsePositiveProjectData createValidTestFalsePositiveProjectData() {
        FalsePositiveProjectData projectData = new FalsePositiveProjectData();
        WebscanFalsePositiveProjectData webScan = new WebscanFalsePositiveProjectData();

        webScan.setCweId(CWE_ID_4711);
        webScan.setMethods(List.of("GET", "POST"));
        webScan.setUrlPattern("https://api.example.com");

        projectData.setWebScan(webScan);

        return projectData;
    }

    private SerecoVulnerability createValidTestVulnerability() {
        SerecoVulnerability vulnerability = new SerecoVulnerability();
        SerecoWeb web = new SerecoWeb();
        vulnerability.getClassification().setCwe("" + CWE_ID_4711);
        vulnerability.setWeb(web);
        vulnerability.setScanType(ScanType.WEB_SCAN);

        SerecoWebRequest request = web.getRequest();
        request.setMethod(METHOD1);
        request.setTarget(TARGET1);
        request.setProtocol(PROTOCOL1);

        return vulnerability;
    }

    private Map<String, Pattern> createMockedTestMap() {
        Map<String, Pattern> projectDataPatternMap = new HashMap<>();
        projectDataPatternMap.put("key", mock(Pattern.class));

        return projectDataPatternMap;
    }
}
