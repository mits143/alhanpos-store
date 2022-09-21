package com.alhanpos.store.model.response.contact

data class ContactListResponse(
    val `data`: List<ContactData>,
    val links: Links,
    val meta: Meta
)