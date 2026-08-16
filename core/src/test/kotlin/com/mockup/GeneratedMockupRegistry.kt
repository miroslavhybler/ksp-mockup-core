package com.mockup

object GeneratedMockupRegistry {

    @JvmStatic
    fun providerHints(): List<ProviderHint> {
        return listOf(
            ProviderHint(
                rawClassName = "com.mockup.core.MockupTest.GenericResponse",
                targetTypeName = "com.example.ListOfUsersResponse",
                providerClassName = "com.example.ListOfUsersResponseMockupProvider",
                accessorName = "listOfUsersResponseMockupProvider",
                accessorImport = "com.example.listOfUsersResponseMockupProvider",
            )
        )
    }

    data class ProviderHint constructor(
        val rawClassName: String,
        val targetTypeName: String,
        val providerClassName: String,
        val accessorName: String,
        val accessorImport: String,
    )
}
