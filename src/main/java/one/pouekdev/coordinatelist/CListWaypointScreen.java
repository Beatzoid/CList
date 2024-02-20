package one.pouekdev.coordinatelist;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import com.google.common.collect.ImmutableList;
import org.apache.commons.compress.utils.Lists;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class CListWaypointScreen extends Screen {
    public CListWaypointScreen(Text title) {
        super(title);
    }
    public ScrollList list;
    @Override
    protected void init() {
        int buttonWidth = 300;

        // Create a margin for all widgets
        int margin = 4;

        // Create a button at the specified position
        ButtonWidget addButton = new ButtonWidget(
                this.width / 2 - buttonWidth / 2, // x-coordinate
                margin,                             // y-coordinate
                buttonWidth,
                20, // Assuming a height of 20, adjust as needed
                Text.translatable("buttons.add.new.waypoint"),
                button -> {
                    PlayerEntity player = MinecraftClient.getInstance().player;
                    CListClient.addNewWaypoint("X: " + Math.round(player.getX()) + " Y: " + Math.round(player.getY()) + " Z: " + Math.round(player.getZ()), false);
                    list.RefreshElements();
                }
        );

        // Set the position of the button
        addButton.x = this.width / 2 - buttonWidth / 2;
        addButton.y = margin;

        // Create and initialize the ScrollList
        list = new ScrollList();
        list.SetupElements();

        // Add the button and ScrollList to the screen
        addDrawableChild(addButton);
        addDrawableChild(list);
    }
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        list.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        list.mouseClicked(mouseX, mouseY, button);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        list.mouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        list.mouseScrolled(mouseX, mouseY, amount);
        return super.mouseScrolled(mouseX, mouseY, amount);
    }
    public class ScrollList extends EntryListWidget<ScrollList.ScrollListEntry> {
        public ScrollList(){
            super(CListWaypointScreen.this.client, CListWaypointScreen.this.width, CListWaypointScreen.this.height, 32, CListWaypointScreen.this.height - 32, 50);
        }
        public void SetupElements() {
            for (int i = 0; i < CListClient.variables.waypoints.size(); i++) {
                final int f_i = i;

                // Create a button at the specified position
                ButtonWidget coordinateButton = new ButtonWidget(
                        0, // x-coordinate (will be set later during layout)
                        0, // y-coordinate (will be set later during layout)
                        150,
                        20, // Assuming a height of 20, adjust as needed
                        Text.literal(CListClient.variables.waypoints.get(i).getCoordinates()),
                        button -> {
                            long window = MinecraftClient.getInstance().getWindow().getHandle();
                            GLFW.glfwSetClipboardString(window, CListClient.variables.waypoints.get(f_i).getCoordinates());
                        }
                );

                // Set the position of the button
                coordinateButton.x = this.width / 2 - coordinateButton.getWidth() / 2;
                coordinateButton.y = i * 24 + 30; // Adjust the vertical spacing as needed

                // Create a ScrollListEntry and add it to the ScrollList
                ScrollList.ScrollListEntry coordinateEntry = new ScrollList.ScrollListEntry(coordinateButton, i, this);
                list.addEntry(coordinateEntry);
            }
        }

        public void RefreshElements(){
            clearEntries();
            SetupElements();
        }
        @Override
        public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            super.render(matrices, mouseX, mouseY, delta);
        }
        @Override
        public int getRowWidth() {
            return 220;
        }
        @Override
        public void drawSelectionHighlight(MatrixStack matrices, int y, int entryWidth, int entryHeight, int borderColor, int fillColor){}
        public void appendNarrations(NarrationMessageBuilder builder){}
        public class ScrollListEntry extends EntryListWidget.Entry<ScrollListEntry>{
            public final ButtonWidget button;
            public final ButtonWidget delete_button;
            public final TextFieldWidget waypoint_name;
            public final Text dimension;
            public final List<Element> children;
            public final int id;

            public ScrollListEntry(ButtonWidget e, int id, ScrollList list){
                this.id = id;
                this.button = e;

                this.delete_button = new ButtonWidget(
                        0, // x-coordinate (will be set later during layout)
                        0, // y-coordinate (will be set later during layout)
                        70,
                        20, // Assuming a height of 20, adjust as needed
                        Text.translatable("buttons.delete.waypoint"),
                        button -> {
                            CListClient.deleteWaypoint(id);
                            list.RefreshElements();
                        }
                );

                // Set the position of the button
                this.delete_button.x =  120 - this.delete_button.getWidth() / 2;
//                this.delete_button.y = /* set the y-coordinate based on your layout */;

//                 Add the button to the screen
//                this.children().add(delete_button);

                this.waypoint_name = new TextFieldWidget(textRenderer, 0, 0, 300, 20, Text.literal(""));
                this.waypoint_name.setFocusUnlocked(true);
                this.waypoint_name.setMaxLength(25);
                this.waypoint_name.setText(CListClient.variables.waypoints.get(id).getName());
                this.dimension = CListClient.variables.waypoints.get(id).getDimension();
                this.children = Lists.newArrayList();
//                this.children.add(button);
//                this.children.add(delete_button);
//                this.children.add(waypoint_name);
            }
            @Override
            public void render(MatrixStack matrices, int index, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float delta) {
                button.x = x-10;
                button.y = y+4;
                delete_button.x = x+140;
                delete_button.y = y+4;
                waypoint_name.y = y+29;
                waypoint_name.setX(x-8);
                waypoint_name.setWidth(width-73);
                button.render(matrices, mouseX, mouseY, delta);
                delete_button.render(matrices, mouseX, mouseY, delta);
                waypoint_name.render(matrices, mouseX, mouseY, delta);
                MinecraftClient.getInstance().textRenderer.drawWithShadow(matrices, dimension.getString(), x+150, y+35, 0xFFFFFF);
            }
            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                boolean handled = false;
                for (Element E : children) {
                    if (E.mouseClicked(mouseX, mouseY, button)) {
                        handled = true;
                        break;
                    }
                }
                return handled || super.mouseClicked(mouseX, mouseY, button);
            }
            @Override
            public boolean mouseReleased(double mouseX, double mouseY, int button) {
                boolean handled = false;
                for (Element E : children) {
                    if (E.mouseReleased(mouseX, mouseY, button)) {
                        handled = true;
                        break;
                    }
                }
                return handled || super.mouseReleased(mouseX, mouseY, button);
            }
            public List<? extends Element> children() {
                return ImmutableList.of(button);
            }
            @Override
            public boolean charTyped(char chr, int keyCode) {
                boolean result = super.charTyped(chr, keyCode);
                waypoint_name.setText(waypoint_name.getText() + chr);
                try{
                    CListClient.variables.waypoints.get(id).setName(waypoint_name.getText());
                }
                catch(IndexOutOfBoundsException ignored){}
                CListClient.variables.saved_since_last_update = false;
                return true;
            }
            @Override
            public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
                if (keyCode == GLFW.GLFW_KEY_BACKSPACE) {
                    if (waypoint_name.getText().length() > 0) {
                        waypoint_name.setText(waypoint_name.getText().substring(0, waypoint_name.getText().length() - 1));
                        CListClient.variables.waypoints.get(id).setName(waypoint_name.getText());
                        CListClient.variables.saved_since_last_update = false;
                    }
                    return true;
                }
                return super.keyPressed(keyCode, scanCode, modifiers);
            }
        }
    }
}