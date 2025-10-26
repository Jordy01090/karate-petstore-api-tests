Feature: Flujo completo PetStore (crear -> consultar -> actualizar -> consultar -> buscar por status)

  Background:
    * url baseUrl
    * def petId = Math.floor(Math.random() * 100000000)
    * def initialName = 'firulais'
    * def updatedName = 'firulais-v2'
    * configure connectTimeout = 15000
    * configure readTimeout = 20000
    * configure retry = { count: 10, interval: 1000 }   # tolerancia a consistencia eventual

  Scenario: CRUD básico y verificación por status (con chequeo opcional por indexación)
    # 1) Crear
    Given path '/pet'
    And request { id: #(petId), name: '#(initialName)', status: 'available' }
    When method POST
    Then status 200
    And match response.id == petId
    And match response.name == initialName
    And match response.status == 'available'

    # 2) Consultar por ID (reintentos por consistencia eventual)
    Given path '/pet', petId
    And retry until responseStatus == 200 && response.id == petId && response.name == initialName
    When method GET
    Then status 200
    And match response.id == petId
    And match response.name == initialName
    And match response.status == 'available'

    # 3) Actualizar a sold
    Given path '/pet'
    And request { id: #(petId), name: '#(updatedName)', status: 'sold' }
    When method PUT
    Then status 200
    And match response.id == petId

    # 4) Verificar por ID tras actualización (reintentos)
    Given path '/pet', petId
    And retry until responseStatus == 200 && response.name == updatedName && response.status == 'sold'
    When method GET
    Then status 200
    And match response.name == updatedName
    And match response.status == 'sold'

    # 5) Chequeo OPCIONAL: buscar por status 'sold'
    #    No falla el test si la API pública no indexa a tiempo.
    Given path '/pet/findByStatus'
    And param status = 'sold'
    When method GET
    Then status 200
    * def maybe = response.find(x => x.id == petId)
    * if (maybe) karate.log('Found in /findByStatus:', maybe); else karate.log('Not indexed in /findByStatus yet (non-blocking)')

  # Negativo
  Scenario: Consultar mascota inexistente
    Given path '/pet', 999999999999
    When method GET
    Then status 404
