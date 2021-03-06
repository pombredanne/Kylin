/*
 * Copyright 2013-2014 eBay Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kylinolap.query.optrule;

import org.eigenbase.rel.FilterRel;
import org.eigenbase.relopt.RelOptRule;
import org.eigenbase.relopt.RelOptRuleCall;
import org.eigenbase.relopt.RelTraitSet;

import com.kylinolap.query.relnode.OLAPFilterRel;
import com.kylinolap.query.relnode.OLAPRel;

/**
 * 
 * @author xjiang
 * 
 */

public class OLAPFilterRule extends RelOptRule {

    public static final RelOptRule INSTANCE = new OLAPFilterRule();

    public OLAPFilterRule() {
        super(operand(FilterRel.class, any()));
    }

    @Override
    public void onMatch(RelOptRuleCall call) {
        FilterRel filter = call.rel(0);

        RelTraitSet traitSet = filter.getTraitSet().replace(OLAPRel.CONVENTION);
        OLAPFilterRel olapFilter = new OLAPFilterRel(filter.getCluster(), traitSet, convert(filter.getChild(), traitSet), filter.getCondition());
        call.transformTo(olapFilter);
    }

}
