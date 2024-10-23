package com.example.inventorymanger.controller.racecondition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.awaitility.Awaitility;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.inventorymanger.controller.ItemController;
import com.example.inventorymanger.repository.ItemRepository;
import com.example.inventorymanger.model.Item;
import com.example.inventorymanger.view.InventoryView;

public class ItemControllerRaceConditionTest {

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private InventoryView studentView;

	@InjectMocks
	private ItemController itemController;

	private AutoCloseable closeable;

	@Before
	public void setUp() throws Exception {
	    closeable = MockitoAnnotations.openMocks(this);
	}

	// @After method as usual
	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

	@Test
	public void testNewItemConcurrent() {
	    List<Item> items = new ArrayList<>();
	    Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	    // stub the ItemRepository
	    when(itemRepository.findById(anyString()))
	        .thenAnswer(invocation -> items.stream()
	            .findFirst().orElse(null));
	    doAnswer(invocation -> {
	        items.add(item);
	        return null;
	    }).when(itemRepository).save(any(Item.class));
	    // start the threads calling addItem concurrently
	    List<Thread> threads = IntStream.range(0, 10)
	        .mapToObj(i -> new Thread(() -> itemController.addItem(item)))
	        .peek(t -> t.start())
	        .collect(Collectors.toList());
	    // wait for all the threads to finish
	    Awaitility.await().atMost(10, TimeUnit.SECONDS)
	        .until(() -> threads.stream().noneMatch(t -> t.isAlive()));
	    // there should be a single element in the list
	    assertThat(items)
	        .containsExactly(item);
	}

	@Test
	public void testDeleteItemConcurrent() {
	    List<Item> items = new ArrayList<>();
	    Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	    // initially add the item to the list
	    items.add(item);
	    // stub the ItemRepository
	    when(itemRepository.findById(anyString()))
	        .thenAnswer(invocation -> items.stream()
	            .findFirst().orElse(null));
	    doAnswer(invocation -> {
	        items.remove(item);
	        return null;
	    }).when(itemRepository).delete(anyString());
	    // start the threads calling deleteItem concurrently
	    List<Thread> threads = IntStream.range(0, 10)
	        .mapToObj(i -> new Thread(() -> itemController.deleteItem(item)))
	        .peek(t -> t.start())
	        .collect(Collectors.toList());
	    // wait for all the threads to finish
	    Awaitility.await().atMost(10, TimeUnit.SECONDS)
	        .until(() -> threads.stream().noneMatch(t -> t.isAlive()));
	    // the list should be empty
	    assertThat(items).isEmpty();
	}

	@Test
	public void testUpdateItemConcurrent() {
	    List<Item> items = new ArrayList<>();
	    Item item = new Item("1", "Laptop", 10, 999.99, "High-end gaming laptop");
	    items.add(item);
	    Item updatedItem = new Item("1", "Updated Laptop", 15, 899.99, "Updated high-end gaming laptop");
	    // stub the ItemRepository
	    when(itemRepository.findById(anyString()))
	        .thenAnswer(invocation -> items.stream()
	            .findFirst().orElse(null));
	    doAnswer(invocation -> {
	        items.clear();
	        items.add(updatedItem);
	        return null;
	    }).when(itemRepository).update(any(Item.class));
	    // start the threads calling updateItem concurrently
	    List<Thread> threads = IntStream.range(0, 10)
	        .mapToObj(i -> new Thread(() -> itemController.updateItem(updatedItem)))
	        .peek(t -> t.start())
	        .collect(Collectors.toList());
	    // wait for all the threads to finish
	    Awaitility.await().atMost(10, TimeUnit.SECONDS)
	        .until(() -> threads.stream().noneMatch(t -> t.isAlive()));
	    // the list should contain the updated item
	    assertThat(items)
	        .containsExactly(updatedItem);
	}


}
