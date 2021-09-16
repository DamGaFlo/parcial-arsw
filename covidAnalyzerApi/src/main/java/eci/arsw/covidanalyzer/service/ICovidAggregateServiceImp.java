/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eci.arsw.covidanalyzer.service;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;



/**
 *
 * @author Home
 */
@Repository
@Qualifier("ICovidAggregateServiceImp")
public class ICovidAggregateServiceImp implements ICovidAggregateService{
    private ConcurrentHashMap<Result,ResultType> results;
    
    public ICovidAggregateServiceImp(){
        results = new ConcurrentHashMap<Result,ResultType>();
    }

    @Override
    public boolean aggregateResult(Result result, ResultType type) {
        boolean ans = false;
        if(!results.containsKey(result)){
            results.put(result, type);
            ans=true;
            System.out.println("agregado");
        }
        System.out.println("no agregado");
        
        return ans;
        
    }

    @Override
    public boolean getResult(ResultType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void upsertPersonWithMultipleTests(UUID id, ResultType type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Set<Result> getAllResults()  {
        Set<Result> ans = new HashSet<Result>();
        for(Result key:results.keySet()){
                ans.add(key);
        }
        return ans;
    }
    @Override
    public Set<Result> getAllResultsByType(ResultType t)  {
        Set<Result> ans = new HashSet<Result>();
        for(Result key:results.keySet()){
            if(results.get(key).equals(t)){
                ans.add(key);
            }
        }
        return ans;
    }
    
    public Set<Result> getAllResultsDate(String date)  {
        Set<Result> ans = new HashSet<Result>();
        for(Result key:results.keySet()){
            String[] fecha = key.getTestDate().split("T");
            if(fecha[0].equals(date)){
                ans.add(key);
            }
        }
        return ans;
    }
    
}
