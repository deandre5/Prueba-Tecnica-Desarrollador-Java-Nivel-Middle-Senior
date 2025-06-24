Feature: Acceptance Test for save Signature

  Background:
    * url api.baseUrl
    * path api.path.stats
    * def bodyRequest = read("../jsonbase/request/request_stats.json")
    * configure logPrettyResponse = true
    * configure logPrettyRequest = true

  @SuccessfullySaveStats
  Scenario Outline: Successfully saves stats
    Given request bodyRequest
    And retry until responseStatus != 502 && responseStatus != 504
    When method POST
    Then status 200
    * print response
    And match response.data.statistics.timeStamp == '#string'
    Examples:
      | totalContactoClientes     | motivoReclamo | motivoGarantia  | motivoDuda         | motivoCompra   | motivoFelicitaciones | motivoCambio | hash                                  |
      | 250                       | 25            | 10              | 100                | 100            | 7                    | 8            | 5484062a4be1ce5645eb414663e14f59      |


  Scenario Outline: Error in data
    Given request bodyRequest
    And retry until responseStatus != 502 && responseStatus != 504
    When method POST
    Then status 400
    * print response
    And match response.errors[0].code == '<code>'
    And match response.errors[0].reason == '<reason>'
    Examples:
      | totalContactoClientes     | motivoReclamo | motivoGarantia  | motivoDuda         | motivoCompra   | motivoFelicitaciones | motivoCambio | hash     | code       | reason                                                                      |
      | 250                       | 25            | 10              | 100                | 100            | 7                    | 8            | 1222     |CCB0002     | Invalid Hash                                                                |
      | 250                       | 25            | 10              | 100                | 100            | 7                    | 8            |          |CCB0016     | An error occurred on request, no hash field was sent                        |
      |                           | 25            | 10              | 100                | 100            | 7                    | 8            | 1222     |CCB0009     | An error occurred on request, no total customers field was sent             |
      | 250                       |               | 10              | 100                | 100            | 7                    | 8            | 1222     |CCB0010     | An error occurred on request, the field ReasonClaim has not been sent       |
      | 250                       | 25            |                 | 100                | 100            | 7                    | 8            | 1222     |CCB0011     | An error occurred on request, The field Warranty reason was not sent.       |
      | 250                       | 25            | 10              |                    | 100            | 7                    | 8            | 1222     |CCB0012     | An error occurred on request, field was not sent reason for doubt           |
      | 250                       | 25            | 10              | 100                |                | 7                    | 8            | 1222     |CCB0013     | An error occurred on request, the Purchase reason field was not sent        |
      | 250                       | 25            | 10              | 100                | 100            |                      | 8            | 1222     |CCB0014     | An error occurred on request, the reason field was not sent Congratulations |
      | 250                       | 25            | 10              | 100                | 100            | 7                    |              | 1222     |CCB0015     | An error occurred on request, no change reason field was sent               |