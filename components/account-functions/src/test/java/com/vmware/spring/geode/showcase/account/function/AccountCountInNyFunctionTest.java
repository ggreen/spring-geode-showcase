package com.vmware.spring.geode.showcase.account.function;

import org.apache.geode.cache.Cache;
import org.apache.geode.cache.execute.RegionFunctionContext;
import org.apache.geode.cache.execute.ResultSender;
import org.apache.geode.cache.query.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountCountInNyFunctionTest
{
    @Mock
    private RegionFunctionContext context;

    @Mock
    private Cache cache;

    @Mock
    private QueryService queryService;

    @Mock
    private Query query;

    private Properties properties = new Properties();

    @Mock
    private ResultSender<Object> resultSender;

    private AccountCountInNyFunction subject;

    @BeforeEach
    void setUp() throws FunctionDomainException, TypeMismatchException, QueryInvocationTargetException,
            NameResolutionException
    {
        subject = new AccountCountInNyFunction();
        subject.initialize(cache,properties);
    }

    @Test
    void execute() throws FunctionDomainException, TypeMismatchException, QueryInvocationTargetException,
            NameResolutionException
    {
        when(context.getResultSender()).thenReturn(resultSender);
        when(query.execute(context)).thenReturn(new ArrayList<Struct>());
        when(queryService.newQuery(anyString())).thenReturn(query);
        when(cache.getQueryService()).thenReturn(queryService);
        subject.initialize(cache,properties);

        subject.execute(context);
    }

    @Test
    void id()
    {
        assertEquals(AccountCountInNyFunction.class.getSimpleName(),subject.getId());
    }
}