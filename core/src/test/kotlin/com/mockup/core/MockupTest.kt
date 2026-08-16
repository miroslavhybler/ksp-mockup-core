package com.mockup.core

import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class MockupTest {

    @Test
    fun missingProviderForGenericClassExplainsTypeErasure() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Mockup.getProvider(GenericResponse::class.java)
        }
        val message = exception.message.orEmpty()

        assertTrue(message.contains("generic parameters"))
        assertTrue(message.contains("typealiases are erased at runtime"))
        assertTrue(message.contains("not a bug in your @Mockup model"))
        assertTrue(message.contains("import com.example.listOfUsersResponseMockupProvider"))
        assertTrue(message.contains("Mockup.listOfUsersResponseMockupProvider.first"))
        assertTrue(message.contains("com.example.ListOfUsersResponse"))
    }

    @Test
    fun missingProviderForGenericClassWithoutHintMentionsGeneratedExtension() {
        val exception = assertThrows(IllegalArgumentException::class.java) {
            Mockup.getProvider(OtherGenericResponse::class.java)
        }
        val message = exception.message.orEmpty()

        assertTrue(message.contains("typealiases are erased at runtime"))
        assertTrue(message.contains("generated Mockup provider extension"))
    }

    private class GenericResponse<T>

    private class OtherGenericResponse<T>
}
