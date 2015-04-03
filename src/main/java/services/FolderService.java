package services;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;

@Service
@Transactional
public class FolderService {
	//Managed repository -----------------------------------------------------
	@Autowired
	private FolderRepository folderRepository;
	
	// Supporting services ---------------------------------------------------
	@Autowired
	private ActorService actorService;
	
	// Constructors-----------------------------------------------------------
	public FolderService()
	{
		
		super();
		
	}
	
	// Simple CRUD methods ---------------------------------------------------
	
	public Collection<Folder>  findAll()
	{
		
		Collection<Folder> all;
		
		all = folderRepository.findAll();
		
		return all;
		
	}
	
	public Folder findOne(int folderId)
	{
		
		Folder result;
		
		result = folderRepository.findOne(folderId);
		
		Assert.notNull(result);
		
		return result;
		
	}
	
	//Other business methods ------------------------------------------------
	
	public Folder findInboxFolderByActorId(int actorId)
	{
		
		Folder result;
		
		result = folderRepository.findInboxFolderByActorId(actorId);
		
		return result;
		
	}
	
	public Folder findOutboxFolderByActorId(int actorId)
	{
		
		Folder result;
		
		result = folderRepository.findOutboxFolderByActorId(actorId);
		
		return result;
		
	}
	
	public Collection<Folder> findAllFoldersByActorId()
	{
		
		Collection<Folder> result;
		Actor actor;
		int actorId;
		
		actor = actorService.findByPrincipal();
		actorId = actor.getId();
		result = folderRepository.findAllFoldersByActorId(actorId);
		
		return result;
	}
	
	public void checkPrincipal(Folder folder)
	{
		
		Actor actor;
		Actor aux;
		
		actor = folder.getActor();
		aux = actorService.findByPrincipal();
		
		Assert.isTrue(actor.equals(aux));
		
	}

}





	
	

	
	

	
	
	

	
	