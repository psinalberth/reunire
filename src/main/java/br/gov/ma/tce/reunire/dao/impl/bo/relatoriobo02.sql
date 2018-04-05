select 
	total, total_categoria, categoria, grupo, modalidade,
	sum(val_din) val_din, sum(val_dat) val_dat, sum(val_dee) val_dee, sum(val_del) val_del, sum(val_dep) val_dep
from (
	select
		'TOTAL (XIV) = (XII + XIII)' total, 
		'SUBTOTAL DAS DESPESAS (XI) = (VIII + IX + X)' total_categoria,
		(case 
			when vw.codigo ~ '^3' then 'Despesas Correntes (VIII)'
	      	when vw.codigo ~ '^4' then 'Despesas de Capital (IX)' 
		end) categoria,
		(case 
			when vw.codigo ~ '^3.1' then 'Pessoal e Encargos Pessoais'
	      	when vw.codigo ~ '^3.2' then 'Juros e Encargos da Dívida'
	      	when vw.codigo ~ '^3.3' then 'Outras Despesas Correntes'
	      	when vw.codigo ~ '^4.4' then 'Investimentos'
	      	when vw.codigo ~ '^4.5' then 'Inversões Financeiras'
	      	when vw.codigo ~ '^4.6' then 'Amortização da Dívida' 
		end) grupo, '' modalidade,
		coalesce(bo.val_din, 0) val_din, coalesce(bo.val_dat, 0) val_dat, coalesce(bo.val_dee, 0) val_dee,
		coalesce(bo.val_del, 0) val_del, coalesce(bo.val_dep, 0) val_dep
	from 
		sae.vw_natureza_despesa vw
	left outer join (
		select
			regexp_replace(bo.natureza_despesa, '[.]', '', 'g') nd,
			sum(bo.dotacao_inicial) val_din, sum(bo.dotacao_atualizada) val_dat, sum(bo.despesa_empenhada) val_dee,
			sum(bo.despesa_liquidada) val_del, sum(bo.despesa_paga) val_dep
		from 
			prestacao.bo02 bo
		where
			bo.unidade_id in (:unidades)
		group by
			regexp_replace(bo.natureza_despesa, '[.]', '', 'g')) bo on bo.nd = regexp_replace(vw.codigo, '[.]', '', 'g')
	where
		vw.codigo ~ '^(3.[123]|4.[45])' and vw.ativo = 'S'
		
	union all
	
	select 
    'TOTAL (XIV) = (XII + XIII)' total, 'SUBTOTAL COM REFINANCIAMENTO (XIII) = (XI + XII)' total_categoria, 'Amortização da Dívida/Refinanciamento (XII)' categoria,
    'Amortização da Dívida Interna' grupo, 'Dívida Mobiliária' modalidade, 0 val_din, 0 val_dat, 0 val_dee,0 val_del, 0 val_dep

  union all

  select 
    'TOTAL (XIV) = (XII + XIII)' total, 'SUBTOTAL COM REFINANCIAMENTO (XIII) = (XI + XII)' total_categoria, 'Amortização da Dívida/Refinanciamento (XII)' categoria,
    'Amortização da Dívida Interna' grupo, 'Outras Dívidas' modalidade, 0 val_din, 0 val_dat, 0 val_dee,0 val_del, 0 val_dep 

  union all

  select 
    'TOTAL (XIV) = (XII + XIII)' total, 'SUBTOTAL COM REFINANCIAMENTO (XIII) = (XI + XII)' total_categoria, 'Amortização da Dívida/Refinanciamento (XII)' categoria,
    'Amortização da Dívida Externa' grupo, 'Dívida Mobiliária' modalidade, 0 val_din, 0 val_dat, 0 val_dee,0 val_del, 0 val_dep 

  union all
  
  select
	'TOTAL (XIV) = (XII + XIII)' total,
	'' total_categoria, '' categoria, 
	'Superávit (XIII)' grupo, '' modalidade, null val_din, null val_dat, null val_dee, null val_del, null val_dep
	
	union all

  select 
    'TOTAL (XIV) = (XII + XIII)' total, 'SUBTOTAL COM REFINANCIAMENTO (XIII) = (XI + XII)' total_categoria, 'Amortização da Dívida/Refinanciamento (XII)' categoria,
    'Amortização da Dívida Externa' grupo, 'Outras Dívidas' modalidade, 0 val_din, 0 val_dat, 0 val_dee,0 val_del, 0 val_dep) result
group by
	total, total_categoria, categoria, grupo, modalidade
order by
	(case 
	    when categoria = 'Despesas Correntes (VIII)' then 1
	    when categoria = 'Despesas de Capital (IX)' then 2
	    when categoria = 'Reserva de Contingência (X)' then 3
	    when categoria = 'Reserva do RPPS (XI)' then 4
	end),
	(case
	    when grupo = 'Amortização da Dívida Interna' then 1
	    when grupo = 'Amortização da Dívida Externa' then 2
	    when grupo = 'Pessoal e Encargos Pessoais' then 3
	    when grupo = 'Juros e Encargos da Dívida' then 4
	    when grupo = 'Outras Despesas Correntes' then 5
	    when grupo = 'Investimentos' then 6
	    when grupo = 'Inversões Financeiras' then 7
	    when grupo = 'Amortização da Dívida' then 8
	end),
	(case 
	    when modalidade = 'Dívida Mobiliária' then 1
	    when modalidade = 'Outras Dívidas' then 2
	end)