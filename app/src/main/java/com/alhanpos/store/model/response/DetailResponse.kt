package com.alhanpos.store.model.response


import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @SerializedName("success")
    val success: Int, // 1
    @SerializedName("transaction")
    val transaction: Transaction
) {
    data class Transaction(
        @SerializedName("additional_expenses")
        val additionalExpenses: List<Any>,
        @SerializedName("additional_notes")
        val additionalNotes: Any?, // null
        @SerializedName("address")
        val address: String, // october, cairo, egypt, 5111111, egypt
        @SerializedName("all_bal_label")
        val allBalLabel: Any?, // null
        @SerializedName("all_due")
        val allDue: String, // ج.م 3,329.25
        @SerializedName("barcode")
        val barcode: String, // Pos0232
        @SerializedName("business_name")
        val businessName: String, // TabCircle
        @SerializedName("cat_code_label")
        val catCodeLabel: Any?, // null
        @SerializedName("client_id")
        val clientId: String, // CO0001
        @SerializedName("client_id_label")
        val clientIdLabel: String, // Client id
        @SerializedName("commission_agent")
        val commissionAgent: String,
        @SerializedName("commission_agent_label")
        val commissionAgentLabel: String,
        @SerializedName("contact")
        val contact: String, // <b>Mobile:</b> 01028404099
        @SerializedName("currency")
        val currency: Currency,
        @SerializedName("currency_symbol")
        val currencySymbol: String, // ج.م
        @SerializedName("customer_custom_fields")
        val customerCustomFields: String,
        @SerializedName("customer_info")
        val customerInfo: String, // Walk-In Customer<br><b>Mobile</b>: 
        @SerializedName("customer_info_address")
        val customerInfoAddress: String, // <br>Walk-In Customer
        @SerializedName("customer_label")
        val customerLabel: String, // Customer
        @SerializedName("customer_mobile")
        val customerMobile: String,
        @SerializedName("customer_name")
        val customerName: String, // Walk-In Customer
        @SerializedName("customer_rp_label")
        val customerRpLabel: Any?, // null
        @SerializedName("customer_tax_label")
        val customerTaxLabel: String,
        @SerializedName("customer_tax_number")
        val customerTaxNumber: Any?, // null
        @SerializedName("customer_total_rp")
        val customerTotalRp: String, // 0
        @SerializedName("date_label")
        val dateLabel: String, // Date
        @SerializedName("date_time_format")
        val dateTimeFormat: String, // m/d/Y
        @SerializedName("delivered_to")
        val deliveredTo: Any?, // null
        @SerializedName("design")
        val design: String, // slim2
        @SerializedName("discount")
        val discount: Int, // 0
        @SerializedName("discount_label")
        val discountLabel: String, // نسبة التحمل
        @SerializedName("discounted_unit_price_label")
        val discountedUnitPriceLabel: String,
        @SerializedName("display_name")
        val displayName: String, // TabCircle, Ahlan POS
        @SerializedName("footer_text")
        val footerText: String, // <p>شكرا لك&nbsp;</p><p>Thanks&nbsp;</p>
        @SerializedName("header_text")
        val headerText: String, // <p>AhlanPOS</p>
        @SerializedName("hide_price")
        val hidePrice: Boolean, // false
        @SerializedName("invoice_date")
        val invoiceDate: String, // 03/27/2023 11:21
        @SerializedName("invoice_heading")
        val invoiceHeading: String, // Invoice
        @SerializedName("invoice_no")
        val invoiceNo: String, // Pos0232
        @SerializedName("invoice_no_prefix")
        val invoiceNoPrefix: String, // Invoice No.
        @SerializedName("is_export")
        val isExport: String, // 0
        @SerializedName("item_discount_label")
        val itemDiscountLabel: String, // نسبة التحمل
        @SerializedName("line_discount_label")
        val lineDiscountLabel: String, // نسبة التحمل
        @SerializedName("line_tax_label")
        val lineTaxLabel: String, // Tax
        @SerializedName("lines")
        val lines: ArrayList<Line>,
        @SerializedName("location_custom_fields")
        val locationCustomFields: String,
        @SerializedName("location_name")
        val locationName: String, // Ahlan POS
        @SerializedName("logo")
        val logo: String, // https://v2.ahlanpos.store/uploads/invoice_logos/1629939160_1629444546_Ahlan-Logo-1.png
        @SerializedName("packing_charge")
        val packingCharge: Int, // 0
        @SerializedName("packing_charge_label")
        val packingChargeLabel: String, // Packing Charge
        @SerializedName("payments")
        val payments: List<Any>,
        @SerializedName("qr_code_details")
        val qrCodeDetails: List<String>,
        @SerializedName("qr_code_text")
        val qrCodeText: String, // Business: TabCircle, Address: Ahlan POS, october, cairo, egypt, 5111111, egypt, Invoice No.: Pos0232, Date: 03/27/2023 11:21, Subtotal: 0, Total: ج.م 21,200.00, Tax: ج.م 0.00, Customer: , https://v2.ahlanpos.store/invoice/9364f160b86add1543077d4b1125fad1
        @SerializedName("round_off")
        val roundOff: String, // ج.م 0.00
        @SerializedName("round_off_amount")
        val roundOffAmount: String, // 0.0000
        @SerializedName("round_off_label")
        val roundOffLabel: String, // Round Off:
        @SerializedName("sales_person")
        val salesPerson: String, // mr admin test
        @SerializedName("sales_person_label")
        val salesPersonLabel: String, // Seller
        @SerializedName("shipping_address")
        val shippingAddress: Any?, // null
        @SerializedName("shipping_charges")
        val shippingCharges: Int, // 0
        @SerializedName("shipping_charges_label")
        val shippingChargesLabel: String, // Shipping Charges
        @SerializedName("shipping_details")
        val shippingDetails: Any?, // null
        @SerializedName("shipping_details_label")
        val shippingDetailsLabel: String, // Shipping Details
        @SerializedName("show_barcode")
        val showBarcode: Boolean, // true
        @SerializedName("show_base_unit_details")
        val showBaseUnitDetails: Boolean, // false
        @SerializedName("show_cat_code")
        val showCatCode: String, // 1
        @SerializedName("show_qr_code")
        val showQrCode: Boolean, // true
        @SerializedName("sub_heading_line1")
        val subHeadingLine1: String,
        @SerializedName("sub_heading_line2")
        val subHeadingLine2: String,
        @SerializedName("sub_heading_line3")
        val subHeadingLine3: String,
        @SerializedName("sub_heading_line4")
        val subHeadingLine4: String,
        @SerializedName("sub_heading_line5")
        val subHeadingLine5: String,
        @SerializedName("subtotal")
        val subtotal: Int, // 0
        @SerializedName("subtotal_exc_tax")
        val subtotalExcTax: String, // ج.م 0.00
        @SerializedName("subtotal_label")
        val subtotalLabel: String, // Subtotal:
        @SerializedName("subtotal_unformatted")
        val subtotalUnformatted: Int, // 0
        @SerializedName("table_product_label")
        val tableProductLabel: String, // Product
        @SerializedName("table_qty_label")
        val tableQtyLabel: String, // Quantity
        @SerializedName("table_subtotal_label")
        val tableSubtotalLabel: String, // Subtotal
        @SerializedName("table_tax_headings")
        val tableTaxHeadings: Any?, // null
        @SerializedName("table_unit_price_label")
        val tableUnitPriceLabel: String, // Unit Price
        @SerializedName("tax")
        val tax: Int, // 0
        @SerializedName("tax_label")
        val taxLabel: String, // Tax:
        @SerializedName("tax_summary_label")
        val taxSummaryLabel: String,
        @SerializedName("taxed_subtotal")
        val taxedSubtotal: String, // ج.م 0.00
        @SerializedName("taxes")
        val taxes: List<Any>,
        @SerializedName("total")
        val total: String, // ج.م 21,200.00
        @SerializedName("total_due")
        val totalDue: Int, // 0
        @SerializedName("total_due_label")
        val totalDueLabel: String, // Total Due
        @SerializedName("total_exempt")
        val totalExempt: String, // ج.م 0.00
        @SerializedName("total_exempt_uf")
        val totalExemptUf: Int, // 0
        @SerializedName("total_in_words")
        val totalInWords: String, // twenty-one thousand two hundred
        @SerializedName("total_label")
        val totalLabel: String, // Total:
        @SerializedName("total_line_discount")
        val totalLineDiscount: Int, // 0
        @SerializedName("total_paid")
        val totalPaid: String, // ج.م 21,200.00
        @SerializedName("total_paid_label")
        val totalPaidLabel: String, // Total Paid
        @SerializedName("total_quantity")
        val totalQuantity: String, // 0.00
        @SerializedName("total_quantity_label")
        val totalQuantityLabel: String, // Total
        @SerializedName("total_unformatted")
        val totalUnformatted: String, // 21200.0000
        @SerializedName("transaction_date")
        val transactionDate: String, // 2023-03-27 11:21:39
        @SerializedName("website")
        val website: String // https://ahlanpos.net
    ) {
        data class Currency(
            @SerializedName("decimal_separator")
            val decimalSeparator: String, // .
            @SerializedName("symbol")
            val symbol: String, // ج.م
            @SerializedName("thousand_separator")
            val thousandSeparator: String // ,
        )

        data class Line(
            @SerializedName("base_unit_multiplier")
            val baseUnitMultiplier: Int, // 1
            @SerializedName("base_unit_name")
            val baseUnitName: String, // PCs
            @SerializedName("cat_code")
            val catCode: String, // SNSOR
            @SerializedName("line_discount")
            val lineDiscount: String, // 0.00
            @SerializedName("line_discount_uf")
            val lineDiscountUf: String, // 0.0000
            @SerializedName("line_total")
            val lineTotal: String, // 0.00
            @SerializedName("line_total_exc_tax")
            val lineTotalExcTax: String, // 0.00
            @SerializedName("line_total_exc_tax_uf")
            val lineTotalExcTaxUf: Int, // 0
            @SerializedName("line_total_uf")
            val lineTotalUf: Int, // 0
            @SerializedName("name")
            val name: String, // Favero Assioma DUO-SHI Pedals 1709
            @SerializedName("orig_quantity")
            val origQuantity: String, // 0.00
            @SerializedName("price_exc_tax")
            val priceExcTax: Int, // 0
            @SerializedName("product_variation")
            val productVariation: String,
            @SerializedName("quantity")
            val quantity: String, // 0.00
            @SerializedName("quantity_uf")
            val quantityUf: Int, // 0
            @SerializedName("sell_line_note")
            val sellLineNote: String,
            @SerializedName("sub_sku")
            val subSku: String, // 705632189023
            @SerializedName("tax")
            val tax: String, // 0.00
            @SerializedName("tax_id")
            val taxId: Any?, // null
            @SerializedName("tax_name")
            val taxName: Any?, // null
            @SerializedName("tax_percent")
            val taxPercent: Any?, // null
            @SerializedName("tax_unformatted")
            val taxUnformatted: String, // 0.0000
            @SerializedName("total_line_discount")
            val totalLineDiscount: String, // 0.00
            @SerializedName("unit_price")
            val unitPrice: String, // 21,200.00
            @SerializedName("unit_price_before_discount")
            val unitPriceBeforeDiscount: String, // 21,200.00
            @SerializedName("unit_price_before_discount_uf")
            val unitPriceBeforeDiscountUf: String, // 21200.0000
            @SerializedName("unit_price_exc_tax")
            val unitPriceExcTax: String, // 21,200.00
            @SerializedName("unit_price_inc_tax")
            val unitPriceIncTax: String, // 21,200.00
            @SerializedName("unit_price_inc_tax_uf")
            val unitPriceIncTaxUf: String, // 21200.0000
            @SerializedName("unit_price_uf")
            val unitPriceUf: String, // 21200.0000
            @SerializedName("units")
            val units: String, // PCs
            @SerializedName("variation")
            val variation: String
        )
    }
}