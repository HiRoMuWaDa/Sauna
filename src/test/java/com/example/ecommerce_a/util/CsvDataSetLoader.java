package com.example.ecommerce_a.util;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.springframework.core.io.Resource;
/**
 * データベースの状態をCSVで設定できるようにするためのクラス
 * お作法のように記述
 */
public class CsvDataSetLoader extends AbstractDataSetLoader {
    @Override
    protected IDataSet createDataSet(Resource resource) throws Exception {
        return new CsvDataSet(resource.getFile());
    }
}
