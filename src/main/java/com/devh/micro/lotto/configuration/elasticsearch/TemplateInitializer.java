package com.devh.micro.lotto.configuration.elasticsearch;

import com.devh.micro.lotto.entity.LottoResultStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.index.AliasAction;
import org.springframework.data.elasticsearch.core.index.AliasActionParameters;
import org.springframework.data.elasticsearch.core.index.AliasActions;
import org.springframework.data.elasticsearch.core.index.PutTemplateRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

/**
 * <pre>
 * Description :
 *     Elasticsearch 템플릿 설정 관련 클래스
 * ===============================================
 * Member fields :
 *
 * ===============================================
 *
 * Author : HeonSeung Kim
 * Date   : 2021/03/11
 * </pre>
 */
@Component
public class TemplateInitializer {
    private static final String TEMPLATE_NAME_LOTTO_RESULT_STORE = "lotto_result_store_template";
    private static final String TEMPLATE_PATTERN_LOTTO_RESULT_STORE = "micro_lotto_store*";
    private final Logger logger = LoggerFactory.getLogger(TemplateInitializer.class);
    private final ElasticsearchOperations operations;

    public TemplateInitializer(@Qualifier("elasticsearchOperations") ElasticsearchOperations operations) {
        this.operations = operations;
    }

    @Autowired
    public void setup() {
        var indexOps = operations.indexOps(LottoResultStore.class);

        if(!indexOps.existsTemplate(TEMPLATE_NAME_LOTTO_RESULT_STORE)) {
            logger.info("Template for " + TEMPLATE_NAME_LOTTO_RESULT_STORE + " does not exist... creating tempates...");

            var mapping = indexOps.createMapping();

            var aliasActions = new AliasActions().add(
                    new AliasAction.Add(AliasActionParameters.builderForTemplate()
                            .withAliases(TEMPLATE_NAME_LOTTO_RESULT_STORE)
                            .build())
            );

            var request = PutTemplateRequest.builder(TEMPLATE_NAME_LOTTO_RESULT_STORE, TEMPLATE_PATTERN_LOTTO_RESULT_STORE)
                    .withMappings(mapping)
                    .withAliasActions(aliasActions)
                    .build();

            logger.info("Complete to create template... alias : " + TEMPLATE_NAME_LOTTO_RESULT_STORE + ", index patterns : " + Arrays.toString(request.getIndexPatterns()));
            logger.info(Objects.requireNonNull(request.getMappings().toJson()));

            indexOps.putTemplate(request);

        }
    }
}
