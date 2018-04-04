select
	total, subtotal, total_categoria, categoria, origem, especie, sum(val_pin) val_pin, sum(val_pat) val_pat, sum(val_rre) val_rre 
from (
select
	'TOTAL (VII) = (V + VI)' total,
	'SUBTOTAL COM REFINANCIAMENTO (V) =  (III + IV)' subtotal, 'SUBTOTAL DAS RECEITAS (III) = (I + II)' total_categoria,
	(case when vw.codigo_natureza_receita ~ '^[179]' then 'Receitas Correntes (I)' else 'Receitas de Capital (II)' end) categoria, 
	(case 
		when vw.codigo_natureza_receita ~ '^[17].1' then 'Receita Tributária'
		when vw.codigo_natureza_receita ~ '^[17].2' then 'Receita de Contribuições'
		when vw.codigo_natureza_receita ~ '^[17].3' then 'Receita Patrimonial'
		when vw.codigo_natureza_receita ~ '^[17].4' then 'Receita Agropecuária'
		when vw.codigo_natureza_receita ~ '^[17].5' then 'Receita Industrial'
		when vw.codigo_natureza_receita ~ '^[17].6' then 'Receita de Serviços'
		when vw.codigo_natureza_receita ~ '^[179].7' then 'Transferências Correntes'
		when vw.codigo_natureza_receita ~ '^[17].9' then 'Outras Receitas Correntes'
		when vw.codigo_natureza_receita ~ '^2.1' then 'Operações de Crédito'
		when vw.codigo_natureza_receita ~ '^[28].2' then 'Alienação de Bens'
		when vw.codigo_natureza_receita ~ '^[28].3' then 'Amortizações de Empréstimos'
		when vw.codigo_natureza_receita ~ '^2.4' then 'Transferências de Capital'
		when vw.codigo_natureza_receita ~ '^2.5' then 'Outras Receitas de Capital'
	end) origem, '' especie,
	coalesce(bo.val_pin, 0) val_pin, coalesce(bo.val_pat, 0) val_pat, coalesce(bo.val_rre, 0) val_rre
from 
	sae.vw_natureza_receita vw 
left outer join (
	select 
		regexp_replace(bo.natureza_receita, '[.]', '', 'g') nr, 
		sum(bo.previsao_inicial) val_pin, sum(bo.previsao_atualizada) val_pat, sum(bo.receita_realizada) val_rre 
	from 
		prestacao.bo01 bo
	where 
		bo.unidade_id in (:unidades)
	group by
		regexp_replace(bo.natureza_receita, '[.]', '', 'g')) bo on bo.nr = regexp_replace(vw.codigo_natureza_receita, '[.]', '', 'g')
where
	vw.codigo_natureza_receita ~ '^([17].[12345679]|[28].[123]|2.[45]|9.[7])' and vw.ativo = 'S' and
		(not vw.codigo_natureza_receita ~ '^2.1.(1.4.06.00|2.3.07.00)')
		
union all

select 'TOTAL (VII) = (V + VI)' total,
	   'SUBTOTAL COM REFINANCIAMENTO (V) =  (III + IV)' subtotal, 'SUBTOTAL DAS RECEITAS (III) = (I + II)' total_categoria, 
	   'Recursos Arrecadados em Exercícios Anteriores (III)' categoria, '' origem, '' especie, null val_pin, null val_pat, null val_rre

union all

select
	'TOTAL (VII) = (V + VI)' total,
	'SUBTOTAL COM REFINANCIAMENTO (V) =  (III + IV)' subtotal, '' total_categoria, 'Operações de Crédito/Refinanciamento (IV)' categoria, 
	(case when vw.codigo_natureza_receita like '2.1.1.%' then 'Operações de Crédito Internas' else 'Operações de Crédito Externas' end) origem,
	(case when vw.codigo_natureza_receita ~ '2.1.(1.1|2.2).01.00' then 'Mobiliária' else 'Contratual' end) especie,
	coalesce(bo.val_pin, 0) val_pin, coalesce(bo.val_pat, 0) val_pat, coalesce(bo.val_rre, 0) val_rre
from 
	sae.vw_natureza_receita vw 
left outer join (
	select 
		regexp_replace(bo.natureza_receita, '[.]', '', 'g') nr, 
		sum(bo.previsao_inicial) val_pin, sum(bo.previsao_atualizada) val_pat, sum(bo.receita_realizada) val_rre 
	from 
		prestacao.bo01 bo
	where 
		bo.unidade_id in (:unidades)
	group by
		regexp_replace(bo.natureza_receita, '[.]', '', 'g')) bo on bo.nr = regexp_replace(vw.codigo_natureza_receita, '[.]', '', 'g')
where
	vw.codigo_natureza_receita ~ '^2.1.((1.1|2.2).01.00|1.4.06.00|2.3.07.00)' and vw.ativo = 'S'
	
union all

select
	'TOTAL (VII) = (V + VI)' total,
	'SUBTOTAL COM REFINANCIAMENTO (V) =  (III + IV)' subtotal, '' total_categoria, 'Operações de Crédito/Refinanciamento (IV)' categoria, 
	'Operações de Crédito Externas' origem, 'Mobiliária' especie, null val_pin, null val_pat, null val_rre
  
union all
  
select
	'TOTAL (VII) = (V + VI)' total,
	'SUBTOTAL COM REFINANCIAMENTO (V) =  (III + IV)' subtotal, '' total_categoria, 'Operações de Crédito/Refinanciamento (IV)' categoria, 
	'Operações de Crédito Externas' origem, 'Contratual' especie, 0 val_pin, 0 val_pat, 0 val_rre
    
union all

select
	'TOTAL (VII) = (V + VI)' total,
	'SUBTOTAL COM REFINANCIAMENTO (V) =  (III + IV)' subtotal, '' total_categoria, 'Operações de Crédito/Refinanciamento (IV)' categoria, 
	'Operações de Crédito Internas' origem, 'Mobiliária' especie, null val_pin, null val_pat, null val_rre
	
union all

select
	'TOTAL (VII) = (V + VI)' total,
	'SUBTOTAL COM REFINANCIAMENTO (V) =  (III + IV)' subtotal, '' total_categoria, 'Operações de Crédito/Refinanciamento (IV)' categoria, 
	'Operações de Crédito Internas' origem, 'Contratual' especie, 0 val_pin, 0 val_pat, 0 val_rre) result
group by
	total, subtotal, total_categoria, categoria, origem, especie
order by
(case 
	when total = 'TOTAL (VII) = (V + VI)' then 1
	when total = 'Saldos de Exercícios Anteriores (Utilizados Para Créditos Adicionais)' then 2
end),
(case 
	when categoria = 'Receitas Correntes (I)' then 1
	when categoria = 'Receitas de Capital (II)' then 2
	when categoria = 'Recursos Arrecadados em Exercícios Anteriores (III)' then 3
    when categoria = 'Operações de Crédito/Refinanciamento (IV)' then 4
end),
(case 
    when origem = 'Receita Tributária' then 1
    when origem = 'Receita de Contribuições' then 2
    when origem = 'Receita Patrimonial' then 3
    when origem = 'Receita Agropecuária' then 4
    when origem = 'Receita Industrial' then 5
    when origem = 'Receita de Serviços' then 6
    when origem = 'Transferências Correntes' then 7
    when origem = 'Outras Receitas Correntes' then 8
    when origem = 'Operações de Crédito' then 9
    when origem = 'Alienação de Bens' then 10
    when origem = 'Amortizações de Empréstimos' then 11
    when origem = 'Transferências de Capital' then 12
    when origem = 'Outras Receitas de Capital' then 13
    when origem = 'Operações de Crédito Internas' then 14
    when origem = 'Operações de Crédito Externas' then 15
  end),
(case 
	when especie = 'Mobiliária' then 1
	when especie = 'Contratual' then 2
end)