webpackJsonp([14],{GZvf:function(t,e,l){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=l("fCQo"),s=l("bQIR"),r=l("YaEn"),i=l("IcnI"),n=l("sQkU"),o=l("cJyC"),u={data:function(){return{admin:{},returnPreviewList:[],returnVo:{listGoods:[{amount:0,commodityName:"string",goodsId:0,id:0,specificationName:"string",unit:"string"}],returnDto:{billId:"string",createTime:"2020-05-31T06:37:28.193Z",orderId:"string",statusId:0,statusName:"string",userAccount:"string",userAddress:"string",userId:"string",userTelephone:"string"}},billReturnTitle:[],dialogVisible:!1,rBillIdList:[],pBillIdList:[],billParams:{billIdList:[]},showReturn:!0,purchasePreviewList:[],purchaseVo:{listGoods:[{amount:0,commodityName:"string",goodsId:0,id:0,price:0,refBillId:"string",specificationName:"string",sumPrice:0,unit:"string"}],purchaseDto:{address:"string",billId:"string",createTime:"2020-06-03T14:30:58.093Z",sendTime:"string",staffId:"string",staffName:"string",staffTelephone:"string",statusId:0,statusName:"string",updateTime:"2020-06-03T14:30:58.093Z"}},billPurchaseTitle:[],pDialogVisible:!1}},created:function(){this.admin=i.a.getters.admin,s.Message.info("请选择待验收还车单或采购单验收"),this.doGetPreviewLList()},methods:{handleDetailClick:function(t){var e=this;this.showReturn?a.a.findReturnVoById(t).then(function(t){e.returnVo=t.data.data,e.billReturnTitle[0]=t.data.data.returnDto}).finally(function(){e.dialogVisible=!0}):o.a.findPurchaseVoById(t).then(function(t){e.purchaseVo=t.data.data,e.billPurchaseTitle[0]=t.data.data.purchaseDto}).finally(function(){e.pDialogVisible=!0})},doGetPreviewLList:function(){var t=this;a.a.findReturnPreviewVoByStatusId(6).then(function(e){t.returnPreviewList=e.data.data.commonBillPreviewList}),o.a.findAllPurchasePreviewByStatusId(6).then(function(e){t.purchasePreviewList=e.data.data.billPreviewList})},handleSelectionChange:function(t){var e=[];t.forEach(function(t){e.push(t.billId)}),this.rBillIdList=e},handleSelectionChange1:function(t){var e=[];t.forEach(function(t){e.push(t.billId)}),this.pBillIdList=e},handleSubmit:function(){var t=this;this.rBillIdList.forEach(function(e){t.billParams.billIdList.push(e)}),this.pBillIdList.forEach(function(e){t.billParams.billIdList.push(e)}),n.a.generateBillCheck(this.billParams).then(function(t){var e=t.data.data;r.a.push({name:"addToCheckSuccess",params:{billId:e}})})},getSummaries:function(t){var e=t.columns,l=t.data,a=[];return e.forEach(function(t,e){if(0!==e){var s=l.map(function(e){return Number(e[t.property])});s.every(function(t){return isNaN(t)})||8!==e?a[e]="N/A":(a[e]=s.reduce(function(t,e){var l=Number(e);return isNaN(l)?t:t+e},0),a[e]+=" 元")}else a[e]="总价"}),a}}},c={render:function(){var t=this,e=t.$createElement,l=t._self._c||e;return l("div",{staticClass:"container"},[l("div",[l("el-radio",{attrs:{label:!0},model:{value:t.showReturn,callback:function(e){t.showReturn=e},expression:"showReturn"}},[t._v("还车单")]),t._v(" "),l("el-radio",{attrs:{label:!1},model:{value:t.showReturn,callback:function(e){t.showReturn=e},expression:"showReturn"}},[t._v("采购单")])],1),t._v(" "),l("el-table",{directives:[{name:"show",rawName:"v-show",value:t.showReturn,expression:"showReturn"}],staticStyle:{width:"100%"},attrs:{data:t.returnPreviewList,border:"",height:"85%",stripe:""},on:{"selection-change":t.handleSelectionChange}},[l("el-table-column",{attrs:{label:"序号",type:"index"}}),t._v(" "),l("el-table-column",{attrs:{label:"还车单编号"},scopedSlots:t._u([{key:"default",fn:function(e){return[l("i",{staticClass:"el-icon-s-order",staticStyle:{"margin-right":"15px"}}),t._v(" "),l("el-button",{attrs:{type:"text"},on:{click:function(l){return t.handleDetailClick(e.row.billId)}}},[t._v(t._s(e.row.billId))])]}}])}),t._v(" "),l("el-table-column",{attrs:{label:"创建时间"},scopedSlots:t._u([{key:"default",fn:function(e){return[l("i",{staticClass:"el-icon-time",staticStyle:{"margin-right":"15px"}}),t._v(" "),l("span",[t._v(t._s(e.row.createTime))])]}}])}),t._v(" "),l("el-table-column",{attrs:{label:"状态"},scopedSlots:t._u([{key:"default",fn:function(e){return[2===e.row.statusId?l("el-tag",{attrs:{type:"danger"}},[t._v(t._s(e.row.statusName))]):7===e.row.statusId?l("el-tag",{attrs:{type:"success"}},[t._v(t._s(e.row.statusName))]):l("el-tag",[t._v(t._s(e.row.statusName))])]}}])}),t._v(" "),l("el-table-column",{attrs:{type:"selection"}})],1),t._v(" "),l("el-table",{directives:[{name:"show",rawName:"v-show",value:!t.showReturn,expression:"!showReturn"}],staticStyle:{width:"100%"},attrs:{data:t.purchasePreviewList,border:"",height:"85%",stripe:""},on:{"selection-change":t.handleSelectionChange1}},[l("el-table-column",{attrs:{label:"序号",type:"index"}}),t._v(" "),l("el-table-column",{attrs:{label:"采购单编号"},scopedSlots:t._u([{key:"default",fn:function(e){return[l("i",{staticClass:"el-icon-s-order",staticStyle:{"margin-right":"15px"}}),t._v(" "),l("el-button",{attrs:{type:"text"},on:{click:function(l){return t.handleDetailClick(e.row.billId)}}},[t._v(t._s(e.row.billId))])]}}])}),t._v(" "),l("el-table-column",{attrs:{label:"创建时间"},scopedSlots:t._u([{key:"default",fn:function(e){return[l("i",{staticClass:"el-icon-time",staticStyle:{"margin-right":"15px"}}),t._v(" "),l("span",[t._v(t._s(e.row.createTime))])]}}])}),t._v(" "),l("el-table-column",{attrs:{label:"修改时间"},scopedSlots:t._u([{key:"default",fn:function(e){return[l("i",{staticClass:"el-icon-time",staticStyle:{"margin-right":"15px"}}),t._v(" "),l("span",[t._v(t._s(e.row.updateTime))])]}}])}),t._v(" "),l("el-table-column",{attrs:{label:"状态"},scopedSlots:t._u([{key:"default",fn:function(e){return[7===e.row.statusId?l("el-tag",{attrs:{type:"success"}},[t._v(t._s(e.row.statusName))]):l("el-tag",[t._v(t._s(e.row.statusName))])]}}])}),t._v(" "),l("el-table-column",{attrs:{type:"selection"}})],1),t._v(" "),l("div",{staticClass:"footer"},[l("el-button",{staticClass:"submit_button",attrs:{type:"success",disabled:0==t.rBillIdList&&0==t.pBillIdList},on:{click:t.handleSubmit}},[t._v("提交")])],1),t._v(" "),l("el-dialog",{attrs:{visible:t.dialogVisible,center:""},on:{"update:visible":function(e){t.dialogVisible=e}}},[l("el-table",{attrs:{data:t.billReturnTitle,border:""}},[l("el-table-column",{attrs:{type:"expand"},scopedSlots:t._u([{key:"default",fn:function(e){return[l("el-form",{staticClass:"table-expand",attrs:{"label-position":"left","label-width":"110px"}},[l("el-form-item",{attrs:{label:"还车单编号："}},[l("span",[t._v(t._s(e.row.billId))])]),t._v(" "),l("el-form-item",{attrs:{label:"创建时间："}},[l("span",[t._v(t._s(e.row.createTime))])]),t._v(" "),l("el-form-item",{attrs:{label:"关联订单编号："}},[l("span",[t._v(t._s(e.row.orderId))])]),t._v(" "),l("el-form-item",{attrs:{label:"用户编号"}},[l("span",[t._v(t._s(e.row.userId))])]),t._v(" "),l("el-form-item",{attrs:{label:"用户账号："}},[l("span",[t._v(t._s(e.row.userAccount))])]),t._v(" "),l("el-form-item",{attrs:{label:"用户电话："}},[l("span",[t._v(t._s(e.row.userTelephone))])]),t._v(" "),l("el-form-item",{attrs:{label:"用户地址："}},[l("span",[t._v(t._s(e.row.userAddress))])]),t._v(" "),l("el-form-item",{attrs:{label:"状态："}},[l("span",[t._v(t._s(e.row.statusName))])])],1)]}}],null,!1,1978670431)}),t._v(" "),l("el-table-column",{attrs:{label:"还车单编号",prop:"billId"}}),t._v(" "),l("el-table-column",{attrs:{label:"创建时间",prop:"createTime"}}),t._v(" "),l("el-table-column",{attrs:{label:"状态",prop:"statusName"}})],1),t._v(" "),l("div",{staticClass:"goods_list"},[l("el-table",{staticStyle:{width:"100%"},attrs:{data:t.returnVo.listGoods,border:""}},[l("el-table-column",{attrs:{label:"序号",type:"index"}}),t._v(" "),l("el-table-column",{attrs:{prop:"goodsId",label:"产品编号"}}),t._v(" "),l("el-table-column",{attrs:{prop:"commodityName",label:"产品名称"}}),t._v(" "),l("el-table-column",{attrs:{prop:"specificationName",label:"产品规格"}}),t._v(" "),l("el-table-column",{attrs:{prop:"unit",label:"单位"}}),t._v(" "),l("el-table-column",{attrs:{prop:"amount",label:"归还数量"}})],1)],1)],1),t._v(" "),l("el-dialog",{attrs:{visible:t.pDialogVisible,center:""},on:{"update:visible":function(e){t.pDialogVisible=e}}},[l("el-table",{attrs:{data:t.billPurchaseTitle,border:""}},[l("el-table-column",{attrs:{type:"expand"},scopedSlots:t._u([{key:"default",fn:function(e){return[l("el-form",{staticClass:"table-expand",attrs:{"label-position":"left","label-width":"100px"}},[l("el-form-item",{attrs:{label:"采购单编号："}},[l("span",[t._v(t._s(e.row.billId))])]),t._v(" "),l("el-form-item",{attrs:{label:"创建时间："}},[l("span",[t._v(t._s(e.row.createTime))])]),t._v(" "),l("el-form-item",{attrs:{label:"修改时间："}},[l("span",[t._v(t._s(e.row.updateTime))])]),t._v(" "),l("el-form-item",{attrs:{label:"发货时间："}},[l("span",[t._v(t._s(e.row.sendTime))])]),t._v(" "),l("el-form-item",{attrs:{label:"收货地址："}},[l("span",[t._v(t._s(e.row.address))])]),t._v(" "),l("el-form-item",{attrs:{label:"员工编号："}},[l("span",[t._v(t._s(e.row.staffId))])]),t._v(" "),l("el-form-item",{attrs:{label:"员工账号："}},[l("span",[t._v(t._s(e.row.staffName))])]),t._v(" "),l("el-form-item",{attrs:{label:"员工电话："}},[l("span",[t._v(t._s(e.row.staffTelephone))])]),t._v(" "),l("el-form-item",{attrs:{label:"状态："}},[l("span",[t._v(t._s(e.row.statusName))])])],1)]}}],null,!1,2738261497)}),t._v(" "),l("el-table-column",{attrs:{label:"编号",prop:"billId"}}),t._v(" "),l("el-table-column",{attrs:{label:"创建时间",prop:"createTime"}}),t._v(" "),l("el-table-column",{attrs:{label:"修改时间",prop:"updateTime"}}),t._v(" "),l("el-table-column",{attrs:{label:"状态",prop:"statusName"}})],1),t._v(" "),l("div",{staticClass:"goods_list"},[l("el-table",{staticStyle:{width:"100%"},attrs:{data:t.purchaseVo.listGoods,border:"","show-summary":"","summary-method":t.getSummaries}},[l("el-table-column",{attrs:{label:"序号",type:"index"}}),t._v(" "),l("el-table-column",{attrs:{prop:"refBillId",label:"关联缺货单编号"}}),t._v(" "),l("el-table-column",{attrs:{prop:"goodsId",label:"产品编号"}}),t._v(" "),l("el-table-column",{attrs:{prop:"commodityName",label:"产品名称"}}),t._v(" "),l("el-table-column",{attrs:{prop:"specificationName",label:"产品规格"}}),t._v(" "),l("el-table-column",{attrs:{prop:"unit",label:"单位"}}),t._v(" "),l("el-table-column",{attrs:{prop:"price",label:"产品单价"}}),t._v(" "),l("el-table-column",{attrs:{prop:"amount",label:"订货数量"}}),t._v(" "),l("el-table-column",{attrs:{prop:"sumPrice",label:"小计"}})],1)],1)],1)],1)},staticRenderFns:[]};var d=l("C7Lr")(u,c,!1,function(t){l("qtqY")},"data-v-62de6101",null);e.default=d.exports},qtqY:function(t,e){}});
//# sourceMappingURL=14.24af7f4d18d3053f427c.js.map