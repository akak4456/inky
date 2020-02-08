let editoro;
ClassicEditor
        .create( document.querySelector( '#editor' ) ,{
        	toolbar:[]
        })
        .catch( error => {
            console.error( error );
        } )
		.then(function(editor){
			editoro = editor;
			editoro.isReadOnly = true;
		});