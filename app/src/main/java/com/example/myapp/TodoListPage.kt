package com.example.myapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TodoListPage(viewModel: TodoViewModel) {


    val todoList by viewModel.todoList.observeAsState()
    var inputTodo by remember {
        mutableStateOf("")
    }
    var selectedSortOption by remember { mutableStateOf("Young") }
    var isSortMenuVisible by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 40.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
            .background(MaterialTheme.colorScheme.primary)
        , horizontalArrangement = Arrangement.End) {
            IconButton(onClick = {isSortMenuVisible = !isSortMenuVisible}, modifier = Modifier.padding(end = 10.dp)) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Sorted",
                    tint = Color.Black
                )
            }
        }
        if(isSortMenuVisible) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, bottom = 8.dp, top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Sorted By:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                RadioButtonSortTodoList(selectedOption = selectedSortOption,
                    onOptionSelected = { newOption ->
                        selectedSortOption = newOption
                        viewModel.sortedBy(newOption)
                    })
                HorizontalDivider(modifier = Modifier.height(24.dp).weight(1f))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {

            OutlinedTextField(
                value = inputTodo, onValueChange = {
                    inputTodo = it
                }, modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                placeholder = { Text("Enter a new task") }
            )
            Button(
                onClick = {
                    if (inputTodo.isNotBlank()) {
                        viewModel.addTodo(inputTodo)
                        inputTodo = ""
                    }
                },
                modifier = Modifier
                    .padding(4.dp)
            ) {
                Text("ADD")
            }
        }

        todoList?.let {
            LazyColumn(content = {
                itemsIndexed(it)
                { _: Int, item: TodoItem ->
                    TodoItemList(item = item, onDelete = {
                        viewModel.deleteTodo(item.id)
                    }, onPriorityChange = { newPriority ->
                        viewModel.updateTodoPriority(item.id, newPriority)
                    })
                }
            }
            )
        } ?: Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "No items yet",
            fontSize = 16.sp
        )
    }
}

@Composable
fun TodoItemList(item: TodoItem, onDelete: () -> Unit, onPriorityChange: (Priority) -> Unit) {
    val flagColor = when (item.priority) {
        Priority.LOW -> Color.White
        Priority.MEDIUM -> Color.Yellow
        Priority.HIGH -> Color.Red
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = SimpleDateFormat("HH:mm:aa, dd/MM", Locale.ENGLISH).format(item.createdAt),
                fontSize = 12.sp,
                color = Color.LightGray
            )
            Text(
                text = item.title,
                fontSize = 20.sp,
                color = Color.White
            )
        }
        IconButton(onClick = {
            val nextPriority = when (item.priority) {
                Priority.LOW -> Priority.MEDIUM
                Priority.MEDIUM -> Priority.HIGH
                Priority.HIGH -> Priority.LOW
            }
            onPriorityChange(nextPriority)
        }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_low_assistant_photo_24),
                contentDescription = "Priority Flag",
                tint = flagColor
            )
        }
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.baseline_delete_24),
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }
}

@Composable
fun RadioButtonSortTodoList(modifier: Modifier = Modifier,selectedOption: String
,onOptionSelected: (String) -> Unit){
    Row(modifier = modifier
        .fillMaxWidth()
        .selectableGroup(),
        horizontalArrangement = Arrangement.SpaceEvenly){
        val radioOptions = listOf("Young","Old","Priority")
        radioOptions.forEach{ text ->
            Row(Modifier
                .height(56.dp)
                .padding(10.dp)
                .selectable(
                    selected = (text == selectedOption),
                    onClick = {onOptionSelected(text)},
                    role = Role.RadioButton
                )
            , verticalAlignment = Alignment.CenterVertically)
            { RadioButton(
                selected = (text == selectedOption),
                onClick = null
            )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

        }
    }
}



