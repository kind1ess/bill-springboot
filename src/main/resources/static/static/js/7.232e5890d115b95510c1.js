webpackJsonp([7],{cjlR:function(t,e){},"d+NK":function(t,e,l){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=l("YXSm"),o=l("bQIR"),s={data:function(){return{odoVo:{goodsWithoutPriceList:[{amount:0,commodityName:"string",goodsId:0,id:0,refBillId:"string",specificationName:"string",unit:"string"}],odoDto:{billId:"string",createTime:"2020-05-27T08:55:57.574Z",staffId:"string",staffName:"string",staffTelephone:"string",statusId:0,statusName:"string",updateTime:"2020-05-27T08:55:57.574Z"}},billOdoTitle:[]}},created:function(){var t=this;a.a.findOdoVoById(this.$route.params.billId).then(function(e){console.log(e),t.odoVo=e.data.data,t.billOdoTitle.push(e.data.data.odoDto),console.log(t.billOdoTitle[0])}).finally(function(){o.Message.success("出库成功！")})}},r={render:function(){var t=this,e=t.$createElement,l=t._self._c||e;return l("div",{staticClass:"container"},[l("el-table",{attrs:{data:t.billOdoTitle,border:""}},[l("el-table-column",{attrs:{type:"expand"},scopedSlots:t._u([{key:"default",fn:function(e){return[l("el-form",{staticClass:"table-expand",attrs:{"label-position":"left","label-width":"100px"}},[l("el-form-item",{attrs:{label:"单据编号："}},[l("span",[t._v(t._s(e.row.billId))])]),t._v(" "),l("el-form-item",{attrs:{label:"创建时间："}},[l("span",[t._v(t._s(e.row.createTime))])]),t._v(" "),l("el-form-item",{attrs:{label:"修改时间："}},[l("span",[t._v(t._s(e.row.updateTime))])]),t._v(" "),l("el-form-item",{attrs:{label:"员工编号："}},[l("span",[t._v(t._s(e.row.staffId))])]),t._v(" "),l("el-form-item",{attrs:{label:"员工姓名："}},[l("span",[t._v(t._s(e.row.staffName))])]),t._v(" "),l("el-form-item",{attrs:{label:"员工电话："}},[l("span",[t._v(t._s(e.row.staffTelephone))])]),t._v(" "),l("el-form-item",{attrs:{label:"状态："}},[l("span",[t._v(t._s(e.row.statusName))])])],1)]}}])}),t._v(" "),l("el-table-column",{attrs:{label:"单据编号",prop:"billId"}}),t._v(" "),l("el-table-column",{attrs:{label:"创建时间",prop:"createTime"}}),t._v(" "),l("el-table-column",{attrs:{label:"更新时间",prop:"updateTime"}}),t._v(" "),l("el-table-column",{attrs:{label:"状态",prop:"statusName"}})],1),t._v(" "),l("div",{staticClass:"goods_list"},[l("el-table",{staticStyle:{width:"100%"},attrs:{data:t.odoVo.goodsWithoutPriceList,border:"",stripe:""}},[l("el-table-column",{attrs:{label:"序号",type:"index"}}),t._v(" "),l("el-table-column",{attrs:{prop:"refBillId",label:"关联订单编号"}}),t._v(" "),l("el-table-column",{attrs:{prop:"goodsId",label:"产品编号"}}),t._v(" "),l("el-table-column",{attrs:{prop:"commodityName",label:"产品名称"}}),t._v(" "),l("el-table-column",{attrs:{prop:"specificationName",label:"产品规格"}}),t._v(" "),l("el-table-column",{attrs:{prop:"unit",label:"单位"}}),t._v(" "),l("el-table-column",{attrs:{prop:"amount",label:"出库数量"}})],1)],1)],1)},staticRenderFns:[]};var i=l("C7Lr")(s,r,!1,function(t){l("cjlR")},"data-v-79b37f8d",null);e.default=i.exports}});
//# sourceMappingURL=7.232e5890d115b95510c1.js.map