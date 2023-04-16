package br.com.alura.livraria.tx;

import javax.enterprise.inject.Specializes;
import javax.interceptor.InvocationContext;

import br.com.rcssoft.rcssoft_lib.tx.TransacionadoPadrao;

@Specializes
public class MeuGerenciadorDeTransacao extends TransacionadoPadrao {

	private static final long serialVersionUID = 1L;
	
	@Override
	public Object executaTransacao(InvocationContext context) {
		System.out.println("Abrindo uma transação");
		em.getTransaction().begin();
		
		try {
			System.out.println("Antes de executar o método interceptado");
			Object resultado = context.proceed();
			
			System.out.println("Antes de comitar");
			em.getTransaction().commit();
			
			return resultado;
		} catch (Exception e) {
			System.out.println("Antes de efetuar o rollback");
			em.getTransaction().rollback();
			throw new RuntimeException(e);
		}
	}

}
