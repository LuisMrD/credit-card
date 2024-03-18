Projeto desafio

Usei o JDK 11 para o projeto.

==============================

Primeiro passo (opcional)

Criar uma nova "conta" em: /account

Metodo: POST
Json:
{
	"name":"Luis Teste",
	"document":"35376768830"
}

name: nome do usuario
document: cpf (requer 11 digitos)

Retorno:
{
	"id": 1,
	"accountNumber": "1528122518571506",
	"name": "Luis Teste",
	"document": "35376768830"
}

accountNumber: numero do "cartão", gerado aleatóriamente com 16 digitos

==============================

Criar nova transação

Criar uma nova "transação" em: /payment

Metodo: POST
Json:
{
	"businessEstablishment":"padaria",
	"value": 66,
	"valueOfInstallments": 66,
	"numberOfInstallments": 1,
	"accountNumber":"1528122518571506",
	"transactionDate":"19/03/2024"
}

businessEstablishment: nome do estabelecimento
value: valor total da compra
valueOfInstallments: valor das parcelas
numberOfInstallments: numero de parcelas
accountNumber: numero do cartão ( gerado no primeiro passo )
transactionDate: data da compra ( dd/MM/yyyy )

Retorno:
{
	"id": 1
}

id: id da fatura atual

Caso número de parcelas seja 1, uma nova fatura é criada para o mês vigente se não houver.
Caso número de parcelas seja maior que 1, uma nova fatura é criada para o mês seguinte,
além de uma nova fatura para o mês vigente se ainda não houver.

==============================

Listar transações

Endpoint: /creditCardBill/{numero do cartao}/{mes}/{ano}
Metodo: GET

Retorno:
[
	{
		"id": 1,
		"businessEstablishment": "padaria",
		"value": 66.0,
		"valueOfInstallments": 66.0,
		"numberOfInstallments": 2,
		"numberOfCurrentInstallment": 1,
		"accountNumber": "7866604200472480",
		"creditCardBillId": 1,
		"transactionDate": 1710817200000
	}
]

Retorna uma lista com todas as transações do mês

==============================

Fechar fatura vigente

Endpoint: /creditCardBill
Metodo: PATCH

Json:
{
	"creditCardBillId": 1,
	"accountNumber" : "1528122518571506"
}

creditCardBillId: id da fatura
accountNumber: numero do cartão

Faz com que o status da fatura seja atualizado para fechado,
e deixa a próxima fatura ( se houver ) com status de aberto.

==============================


    
